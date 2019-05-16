package com.xia.sell.dao;

import com.xia.sell.po.OrderComment;

import java.util.List;

public interface OrderCommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(OrderComment record);

    int insertSelective(OrderComment record);

    OrderComment selectByPrimaryKey(String commentId);

    int updateByPrimaryKeySelective(OrderComment record);

    int updateByPrimaryKey(OrderComment record);

	List<OrderComment> selectBySellerId(String sellerId);
}