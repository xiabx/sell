package com.xia.sell.dao;

import com.xia.sell.po.SellerDiscount;

import java.util.List;

public interface SellerDiscountMapper {
    int deleteByPrimaryKey(String discountId);

    int insert(SellerDiscount record);

    int insertSelective(SellerDiscount record);

    SellerDiscount selectByPrimaryKey(String discountId);

    int updateByPrimaryKeySelective(SellerDiscount record);

    int updateByPrimaryKey(SellerDiscount record);

	List<SellerDiscount> selectBySellerId(String sellerId);
}