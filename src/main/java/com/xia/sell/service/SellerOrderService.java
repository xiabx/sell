package com.xia.sell.service;

import java.util.HashMap;
import java.util.Map;

public interface SellerOrderService {
	HashMap<String, Object> getAllOrder(Integer page, String sellerId);

	void cancelOrder(String orderId);

	Map<String,Object> findOne(String orderId);

	void alreadyOrder(String orderId);

	void setDistribution(String orderId);

	void setDistributionFinish(String orderId);
}
