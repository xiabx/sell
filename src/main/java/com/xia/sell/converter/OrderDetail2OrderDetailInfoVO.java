package com.xia.sell.converter;

import com.xia.sell.po.OrderDetail;
import com.xia.sell.vo.OrderDetailInfoVO;
import org.springframework.beans.BeanUtils;

public class OrderDetail2OrderDetailInfoVO {

	public static OrderDetailInfoVO toConverter(OrderDetail orderDetail){
		OrderDetailInfoVO orderDetailInfoVO = new OrderDetailInfoVO();
		BeanUtils.copyProperties(orderDetail, orderDetailInfoVO);
		return orderDetailInfoVO;
	}
}
