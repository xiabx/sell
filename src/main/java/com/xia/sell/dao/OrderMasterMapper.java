package com.xia.sell.dao;

import com.xia.sell.po.OrderMaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderMasterMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderMaster record);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMaster record);

    int updateByPrimaryKey(OrderMaster record);

	OrderMaster selectByOpenidAndOrderId(Map map);

	List<OrderMaster> selectByBuyerOpenId(String buyerOpenId);

	List<OrderMaster> selectAllOrders(String sellerId);

	OrderMaster selectByOrderId(String orderId);

	double selectDecimalByOpenidOrderId(HashMap<String, String> map);
}