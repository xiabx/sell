package com.xia.sell.service;

import com.xia.sell.dto.OrderCreateDTO;
import com.xia.sell.vo.BuyerOrderDetailVO;
import com.xia.sell.vo.OrderInfoVO;

import java.util.Map;

public interface OrderService {

	Map<String,Object> createOrder(OrderCreateDTO orderCreateDTO);

	OrderInfoVO findOne(String buyerOpenId,String orderId);

	Map<String,Object> findList(String buyerOpenid, Integer page);

	boolean cancel(String orderId,String buyerOpenid);

	boolean finish(String orderId,String buyerOpenid);

	Map<String,Object> paySuccess(String orderId);

	BuyerOrderDetailVO findOneOrder(String buyerOpenid, String orderId);
}
