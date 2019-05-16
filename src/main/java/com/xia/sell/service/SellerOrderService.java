package com.xia.sell.service;

import com.xia.sell.vo.OrderInfoVO;

import java.util.HashMap;

public interface SellerOrderService {
	HashMap<String, Object> getAllOrder(Integer page, String sellerId);

	void cancelOrder(String orderId);

	OrderInfoVO findOne(String orderId);

	void alreadyOrder(String orderId);

	void setDistribution(String orderId);

	void setDistributionFinish(String orderId);
}
