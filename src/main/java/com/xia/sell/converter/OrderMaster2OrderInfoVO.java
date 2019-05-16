package com.xia.sell.converter;

import com.xia.sell.po.OrderMaster;
import com.xia.sell.vo.OrderInfoVO;
import org.springframework.beans.BeanUtils;

public class OrderMaster2OrderInfoVO {
	public static OrderInfoVO toConverter(OrderMaster orderMaster){
		OrderInfoVO orderInfoVO = new OrderInfoVO();
		BeanUtils.copyProperties(orderMaster, orderInfoVO);
		return orderInfoVO;
	}
}
