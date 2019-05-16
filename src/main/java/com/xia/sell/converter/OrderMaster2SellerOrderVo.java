package com.xia.sell.converter;

import com.xia.sell.enums.OrderStatusEnum;
import com.xia.sell.po.OrderMaster;
import com.xia.sell.util.EnumUtil;
import com.xia.sell.vo.SellerOrderVO;
import org.springframework.beans.BeanUtils;

public class OrderMaster2SellerOrderVo {

	public static SellerOrderVO toConverter(OrderMaster orderMaster){
		SellerOrderVO sellerOrderVO = new SellerOrderVO();
		BeanUtils.copyProperties(orderMaster, sellerOrderVO);
		sellerOrderVO.setDetail(orderMaster.getBuyerAddress());
		sellerOrderVO.setLocation(orderMaster.getBuyerLocation());
		sellerOrderVO.setOrderStatus(EnumUtil.getMessage(orderMaster.getOrderStatus(), OrderStatusEnum.class));
		return sellerOrderVO;
	}
}
