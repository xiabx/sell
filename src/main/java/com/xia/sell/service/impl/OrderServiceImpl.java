package com.xia.sell.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xia.sell.converter.OrderDetail2OrderDetailInfoVO;
import com.xia.sell.converter.OrderMaster2OrderInfoVO;
import com.xia.sell.dao.*;
import com.xia.sell.dto.OrderCreateDTO;
import com.xia.sell.dto.OrderProductInfoDTO;
import com.xia.sell.enums.OrderStatusEnum;
import com.xia.sell.enums.ResultEnum;
import com.xia.sell.exception.SellException;
import com.xia.sell.po.*;
import com.xia.sell.service.OrderService;
import com.xia.sell.service.WechatSessionService;
import com.xia.sell.util.CheckTimeOut;
import com.xia.sell.util.Distance;
import com.xia.sell.util.EnumUtil;
import com.xia.sell.util.KeyUtil;
import com.xia.sell.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private OrderMasterMapper orderMasterMapper;
	@Autowired
	private SellerDiscountMapper sellerDiscountMapper;
	@Autowired
	private SellerInfoMapper sellerInfoMapper;
	@Autowired
	private BuyerAddressMapper buyerAddressMapper;
	@Autowired
	private WechatSessionService wechatSessionService;

	@Override
	//创建订单
	@Transactional
	public Map<String, Object> createOrder(OrderCreateDTO orderCreateDTO) {
		//检验是否超过配送范围
		SellerInfo sellerInfo = sellerInfoMapper.selectByPrimaryKey(orderCreateDTO.getSellerId());
		BuyerAddress buyerAddress = buyerAddressMapper.selectByPrimaryKey(orderCreateDTO.getAddressId());
		double distance = Distance.getDistance(Double.parseDouble(sellerInfo.getLongitude()), Double.parseDouble(sellerInfo.getLatitude()), Double.parseDouble(buyerAddress.getLongitude()), Double.parseDouble(buyerAddress.getLatitude()));
		if (distance > 10000){
			throw new SellException(ResultEnum.MORE_THAN_DISTANCE);
		}

		List<OrderProductInfoDTO> items = orderCreateDTO.getItems();
		BigDecimal sum = new BigDecimal(BigInteger.ZERO);
		//订单id
		String orderId = KeyUtil.getOrderMasterKey();
		int productCount = 0;
		//计算订单总价,设置订单详情，设置销量
		for (OrderProductInfoDTO item : items) {
			String productId = item.getProductId();
			ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
			if (productInfo == null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST,productId);
			}
			Integer productQuantity = item.getProductQuantity();
			BigDecimal orderAmount = productInfo.getProductPrice().multiply(BigDecimal.valueOf(productQuantity));
			sum =sum.add(orderAmount);
			OrderDetail itemOrderDetail = new OrderDetail();
			itemOrderDetail.setDetailId(KeyUtil.getOrderDetailKey());
			itemOrderDetail.setOrderId(orderId);
			itemOrderDetail.setProductQuantity(item.getProductQuantity());
			BeanUtils.copyProperties(productInfo, itemOrderDetail);

			//存储订单中一个商品的订单详情
			orderDetailMapper.insertSelective(itemOrderDetail);
			//更新商品的销量
			productInfo.setSold(productInfo.getSold()+item.getProductQuantity());
			productInfoMapper.updateByPrimaryKeySelective(productInfo);
			productCount +=item.getProductQuantity();
		}
		//商家销量
		sellerInfo.setShopSold(sellerInfo.getShopSold()+productCount);
		sellerInfoMapper.updateByPrimaryKeySelective(sellerInfo);

		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderCreateDTO, orderMaster);
		orderMaster.setBuyerName(buyerAddress.getBuyerName());
		orderMaster.setBuyerAddress(buyerAddress.getDetail());
		orderMaster.setBuyerLocation(buyerAddress.getLocation());
		orderMaster.setBuyerPhone(buyerAddress.getBuyerPhone());
		orderMaster.setOrderId(orderId);

		List<SellerDiscount> sellerDiscounts = sellerDiscountMapper.selectBySellerId(orderCreateDTO.getSellerId());
		SellerDiscount sellerDiscount1=null;
		for (SellerDiscount sellerDiscount : sellerDiscounts) {
			if (sum.intValue()> sellerDiscount.getFull()){
				sellerDiscount1=sellerDiscount;
			}
		}
		if (sellerDiscount1!=null){
			sum = sum.add(new BigDecimal(sellerDiscount1.getReduce()).negate());
		}
		Integer distributionFee = sellerInfoMapper.selectByPrimaryKey(orderCreateDTO.getSellerId()).getDistributionFee();
		sum = sum.add(new BigDecimal(distributionFee));
		orderMaster.setOrderAmount(sum);
		String openid = wechatSessionService.getOpenid(orderCreateDTO.getSessionId());
		orderMaster.setBuyerOpenid(openid);
		orderMaster.setOrderStatus(OrderStatusEnum.TO_PAY.getCode());
		//存储订单详情
		orderMasterMapper.insertSelective(orderMaster);

		HashMap<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		return map;
	}
	//查询单个订单
	@Transactional
	public OrderInfoVO findOne(String buyerOpenid,String orderId){
		//查询订单
		HashMap<String, String> map = new HashMap<>();
		map.put("buyerOpenid", buyerOpenid);
		map.put("orderId", orderId);
		OrderMaster orderMaster=orderMasterMapper.selectByOpenidAndOrderId(map);
		if (orderMaster == null){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		//根据订单号。查询购买的商品
		List<OrderDetail> orderDetails = orderDetailMapper.selectDetailByOrderId(orderMaster.getOrderId());
		if (orderDetails == null){
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		OrderInfoVO orderInfoVO = OrderMaster2OrderInfoVO.toConverter(orderMaster);
		ArrayList<OrderDetailInfoVO> orderDetailInfoVOS = new ArrayList<>();
		for (OrderDetail orderDetail : orderDetails) {
			orderDetailInfoVOS.add(OrderDetail2OrderDetailInfoVO.toConverter(orderDetail));
		}
		orderInfoVO.setItems(orderDetailInfoVOS);
		return orderInfoVO;
	}
    //查询一个用户的所有订单
	@Transactional
	public Map<String,Object> findList(String buyerOpenid, Integer page){
		PageHelper.startPage(page, 10);
		List<OrderMaster> orderMasters = orderMasterMapper.selectByBuyerOpenId(buyerOpenid);
		PageInfo<OrderMaster> orderMasterPageInfo = new PageInfo<>(orderMasters);
		List<BuyerOrderListVO> vos = new ArrayList<>();
		for (OrderMaster orderMaster : orderMasters) {
			BuyerOrderListVO vo = new BuyerOrderListVO();
			BeanUtils.copyProperties(orderMaster, vo);
			//订单状态为未支付状态  0
			if (orderMaster.getOrderStatus() == OrderStatusEnum.TO_PAY.getCode()){
				//超时
				if (CheckTimeOut.isTimeOut(orderMaster.getCreateTime())){
					cancel(orderMaster.getOrderId(), buyerOpenid);
					vo.setOrderStatus(OrderStatusEnum.CANCEL.getMessage());
				}else {
					vo.setOrderStatus(OrderStatusEnum.TO_PAY.getMessage());
				}
			}else {
				vo.setOrderStatus(EnumUtil.getMessage(orderMaster.getOrderStatus(), OrderStatusEnum.class));
			}
			vo.setAmount(orderMaster.getOrderAmount().toString());
			List<OrderDetail> orderDetails = orderDetailMapper.selectDetailByOrderId(orderMaster.getOrderId());
			if (orderDetails.size()>1){
				vo.setProductName(orderDetails.get(0).getProductName()+"  等");
			}else{
				vo.setProductName(orderDetails.get(0).getProductName());
			}
			SellerInfo sellerInfo = sellerInfoMapper.selectByPrimaryKey(orderMaster.getSellerId());

			vo.setCreateTime(orderMaster.getCreateTime());
			vo.setShopName(sellerInfo.getShopName());
			vo.setShopIcon(sellerInfo.getShopIcon());
			vos.add(vo);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", vos);
		map.put("isLastPage", orderMasterPageInfo.isIsLastPage());
		return map;
	}
	@Transactional
	//取消订单
	public boolean cancel(String orderId,String buyerOpenid){
		HashMap<String, String> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("buyerOpenid", buyerOpenid);
		OrderMaster orderMaster = orderMasterMapper.selectByOpenidAndOrderId(map);
		//订单不存在
		if (orderMaster==null){
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		//被取消订单不是新订单
		if (orderMaster.getOrderStatus() != OrderStatusEnum.TO_PAY.getCode()){
			throw new SellException(ResultEnum.ORDERSTATUS_NOT_NEW);
		}
		//设置订单状态
		orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		int productCount = 0;
		//返还销量
		List<OrderDetail> orderDetails = orderDetailMapper.selectDetailByOrderId(orderMaster.getOrderId());
		for (OrderDetail orderDetail : orderDetails) {
			ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(orderDetail.getProductId());
			if (productInfo==null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			productInfo.setSold(productInfo.getSold()-orderDetail.getProductQuantity());
			productInfoMapper.updateByPrimaryKeySelective(productInfo);
			productCount += orderDetail.getProductQuantity();
		}
		//商家销量
		SellerInfo sellerInfo = sellerInfoMapper.selectByPrimaryKey(orderMaster.getSellerId());
		sellerInfo.setShopSold(sellerInfo.getShopSold()-productCount);
		sellerInfoMapper.updateByPrimaryKeySelective(sellerInfo);

		orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
		return true;
	}
	//设置订单完成
	@Transactional
	public boolean finish(String orderId,String buyerOpenid){
		HashMap<String, String> map = new HashMap<>();
		map.put("orderId", orderId);
		map.put("buyerOpenid", buyerOpenid);
		OrderMaster orderMaster = orderMasterMapper.selectByOpenidAndOrderId(map);
		//订单不存在
		if (orderMaster==null){
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		//被取消订单不是新订单
		if (orderMaster.getOrderStatus() != OrderStatusEnum.TO_PAY.getCode()){
			throw new SellException(ResultEnum.ORDERSTATUS_NOT_NEW);
		}
		//设置订单状态
		orderMaster.setOrderStatus(OrderStatusEnum.COMMENT_FINISH.getCode());
		orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
		return true;
	}
	//支付订单
	@Override
	public Map<String, Object> paySuccess(String orderId) {
		OrderMaster orderMaster = new OrderMaster();
		orderMaster.setOrderId(orderId);
		//orderMaster.setPayStatus(OrderStatusEnum..getCode());
		orderMaster.setOrderStatus(OrderStatusEnum.ALREADY_PAY.getCode());
		orderMasterMapper.updateByPrimaryKeySelective(orderMaster);

		OrderMaster orderMaster1 = orderMasterMapper.selectByPrimaryKey(orderId);
		List<OrderDetail> orderDetails = orderDetailMapper.selectDetailByOrderId(orderId);
		//返回给商家端的新订单数据
		ArrayList<SellerNewOrderDetailVO> newOrderVOS = new ArrayList<>();
		for (OrderDetail orderDetail : orderDetails) {
			SellerNewOrderDetailVO vo = new SellerNewOrderDetailVO();
			vo.setProductId(orderDetail.getProductId());
			vo.setProductPrice(orderDetail.getProductPrice().toString());
			vo.setProductName(orderDetail.getProductName());
			vo.setProductQuantity(orderDetail.getProductQuantity());
			vo.setItemAmount(orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
			newOrderVOS.add(vo);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("sellerId", orderMaster1.getSellerId());
		map.put("newOrderList", newOrderVOS);
		map.put("amount", orderMaster1.getOrderAmount());

		//查询订单
		OrderMaster orderMaster11=orderMasterMapper.selectByOrderId(orderId);
		if (orderMaster == null){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		//根据订单号。查询购买的商品
		List<OrderDetail> orderDetails1 = orderDetailMapper.selectDetailByOrderId(orderMaster11.getOrderId());
		if (orderDetails == null){
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		OrderInfoVO orderInfoVO = OrderMaster2OrderInfoVO.toConverter(orderMaster11);
		ArrayList<OrderDetailInfoVO> orderDetailInfoVOS = new ArrayList<>();
		for (OrderDetail orderDetail : orderDetails1) {
			orderDetailInfoVOS.add(OrderDetail2OrderDetailInfoVO.toConverter(orderDetail));
		}
		orderInfoVO.setItems(orderDetailInfoVOS);
		map.put("orderMaster", orderMaster11);
		map.put("orderDetails", orderDetails1);
		String status = EnumUtil.getMessage(orderMaster.getOrderStatus(), OrderStatusEnum.class);
		map.put("status", status);
		return map;


	}

	@Override
	@Transactional
	public BuyerOrderDetailVO findOneOrder(String buyerOpenid, String orderId) {
		HashMap<String, String> map = new HashMap<>();
		map.put("buyerOpenid", buyerOpenid);
		map.put("orderId", orderId);
		OrderMaster orderMaster = orderMasterMapper.selectByOpenidAndOrderId(map);
		BuyerOrderDetailVO buyerOrderDetailVO = new BuyerOrderDetailVO();
		BeanUtils.copyProperties(orderMaster,buyerOrderDetailVO);
		//订单状态为未支付状态  0
		if (orderMaster.getOrderStatus() == OrderStatusEnum.TO_PAY.getCode()){
			//超时 自动取消
			if (CheckTimeOut.isTimeOut(orderMaster.getCreateTime())){
				cancel(orderMaster.getOrderId(), buyerOpenid);
				//取消
				buyerOrderDetailVO.setOrderStatusCode(Integer.valueOf(OrderStatusEnum.CANCEL.getCode()));
				buyerOrderDetailVO.setOrderStatus(OrderStatusEnum.CANCEL.getMessage());
			}else {
				//未超时 待支付
				buyerOrderDetailVO.setMinute(CheckTimeOut.getMinute(orderMaster.getCreateTime()));
				buyerOrderDetailVO.setSecond(CheckTimeOut.getSecond(orderMaster.getCreateTime()));
				buyerOrderDetailVO.setOrderStatus(OrderStatusEnum.TO_PAY.getMessage());
				buyerOrderDetailVO.setOrderStatusCode(Integer.valueOf(OrderStatusEnum.TO_PAY.getCode()));
			}
		}else {
			buyerOrderDetailVO.setOrderStatusCode(Integer.valueOf(orderMaster.getOrderStatus()));
			buyerOrderDetailVO.setOrderStatus(EnumUtil.getMessage(orderMaster.getOrderStatus(), OrderStatusEnum.class));
		}
		List<OrderDetail> orderDetails = orderDetailMapper.selectDetailByOrderId(orderMaster.getOrderId());
		ArrayList<BuyerOrderDetailVO.cartItem> cartItems = new ArrayList<>();
		for (OrderDetail orderDetail : orderDetails) {
			BuyerOrderDetailVO.cartItem cartItem = buyerOrderDetailVO.new cartItem();
			BeanUtils.copyProperties(orderDetail, cartItem);
			cartItems.add(cartItem);
		}
		buyerOrderDetailVO.setCart(cartItems);
		SellerInfo sellerInfo = sellerInfoMapper.selectByPrimaryKey(orderMaster.getSellerId());
		List<SellerDiscount> sellerDiscounts = sellerDiscountMapper.selectBySellerId(orderMaster.getSellerId());
		SellerDiscount discount=null;
		for (SellerDiscount sellerDiscount : sellerDiscounts) {
			if (Double.parseDouble(orderMaster.getOrderAmount().toString())+sellerDiscount.getReduce()>sellerDiscount.getFull()){
				discount=sellerDiscount;
			}
		}
		buyerOrderDetailVO.setReduce(discount.getReduce());
		BeanUtils.copyProperties(sellerInfo, buyerOrderDetailVO);
		return buyerOrderDetailVO;
	}

	//付款前查询订单金额
	public BigDecimal getAmount(String openid, String orderId) {
		HashMap<String, String> map = new HashMap<>();
		map.put("buyerOpenid", openid);
		map.put("orderId", orderId);
		OrderMaster orderMaster = orderMasterMapper.selectByOpenidAndOrderId(map);
		return orderMaster.getOrderAmount();
	}
}
