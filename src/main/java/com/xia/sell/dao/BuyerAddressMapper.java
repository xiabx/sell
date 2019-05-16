package com.xia.sell.dao;

import com.xia.sell.po.BuyerAddress;

import java.util.List;

public interface BuyerAddressMapper {
    int deleteByPrimaryKey(String addressId);

    int insert(BuyerAddress record);

    int insertSelective(BuyerAddress record);

    BuyerAddress selectByPrimaryKey(String addressId);

    int updateByPrimaryKeySelective(BuyerAddress record);

    int updateByPrimaryKey(BuyerAddress record);

	List<BuyerAddress> selectByOpenid(String openid);
}