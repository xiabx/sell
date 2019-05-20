package com.xia.sell.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xia.sell.converter.OrderDetail2OrderDetailInfoVO;
import com.xia.sell.converter.OrderMaster2OrderInfoVO;
import com.xia.sell.converter.OrderMaster2SellerOrderVo;
import com.xia.sell.dao.BuyerAddressMapper;
import com.xia.sell.dao.OrderDetailMapper;
import com.xia.sell.dao.OrderMasterMapper;
import com.xia.sell.enums.OrderStatusEnum;
import com.xia.sell.enums.ResultEnum;
import com.xia.sell.exception.SellException;
import com.xia.sell.po.OrderDetail;
import com.xia.sell.po.OrderMaster;
import com.xia.sell.service.SellerOrderService;
import com.xia.sell.util.EnumUtil;
import com.xia.sell.vo.OrderDetailInfoVO;
import com.xia.sell.vo.OrderInfoVO;
import com.xia.sell.vo.SellerOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SellerOrderServiceImpl implements SellerOrderService {
	@Autowired
	private OrderMasterMapper orderMasterMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private BuyerAddressMapper buyerAddressMapper;
	@Override
	public HashMap<String, Object>  getAllOrder(Integer page,String sellerId) {
		PageHelper.startPage(page, 10);
		List<OrderMaster> orderMasters = orderMasterMapper.selectAllOrders(sellerId);
		PageInfo<OrderMaster> vosPage = new PageInfo<>(orderMasters);

		List<SellerOrderVO> sellerOrderVOs = new ArrayList<>();
		for (OrderMaster orderMaster : orderMasters) {
			SellerOrderVO sellerOrderVO = OrderMaster2SellerOrderVo.toConverter(orderMaster);
			sellerOrderVOs.add(sellerOrderVO);
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", sellerOrderVOs);
		map.put("isFirstPage", vosPage.isIsFirstPage());
		map.put("isLastPage", vosPage.isIsLastPage());
		map.put("prePage", vosPage.getPrePage());
		map.put("nextPage", vosPage.getNextPage());
		map.put("pages", vosPage.getPages());
		map.put("pageNum", vosPage.getPageNum());
		map.put("total", vosPage.getTotal());
		return map;
	}

	@Override
	@Transactional
	public void cancelOrder(String orderId) {
		OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
		if (orderMaster == null){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		if (orderMaster.getOrderStatus()!=OrderStatusEnum.ALREADY_PAY.getCode()){
			throw new SellException(ResultEnum.ORDERSTATUS_NOT_NEW);
		}
		orderMaster =new OrderMaster();
		orderMaster.setOrderId(orderId);
		orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
	}

	@Override
	public Map findOne(String orderId) {
		//查询订单
		OrderMaster orderMaster=orderMasterMapper.selectByOrderId(orderId);
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
		HashMap<String, Object> orderDetail = new HashMap<>();
		orderDetail.put("orderMaster", orderMaster);
		orderDetail.put("orderDetails", orderDetails);
		String status = EnumUtil.getMessage(orderMaster.getOrderStatus(), OrderStatusEnum.class);
		orderDetail.put("status", status);
		return orderDetail;
	}

	@Override
	//卖家接单
	public void alreadyOrder(String orderId) {
		OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
		if (orderMaster == null){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		if (orderMaster.getOrderStatus()!=1){
			throw new SellException(ResultEnum.ORDERSTATUS_NOT_NEW);
		}
		orderMaster =new OrderMaster();
		orderMaster.setOrderId(orderId);
		orderMaster.setOrderStatus(OrderStatusEnum.ALREADY_ORDER.getCode());
		orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
	}

	@Override
	//设置订单为正在配送
	public void setDistribution(String orderId) {
		OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
		if (orderMaster == null){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		if (orderMaster.getOrderStatus()!=OrderStatusEnum.ALREADY_ORDER.getCode()){
			throw new SellException(ResultEnum.ORDERSTATUS_NOT_NEW);
		}
		orderMaster =new OrderMaster();
		orderMaster.setOrderId(orderId);
		orderMaster.setOrderStatus(OrderStatusEnum.DISTRIBUTIONING.getCode());
		orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
	}

	@Override
	public void setDistributionFinish(String orderId) {
		OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
		if (orderMaster == null){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		if (orderMaster.getOrderStatus()!=OrderStatusEnum.DISTRIBUTIONING.getCode()){
			throw new SellException(ResultEnum.ORDERSTATUS_NOT_NEW);
		}
		orderMaster =new OrderMaster();
		orderMaster.setOrderId(orderId);
		orderMaster.setOrderStatus(OrderStatusEnum.WAITE_COMMENT.getCode());
		orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
	}

}
