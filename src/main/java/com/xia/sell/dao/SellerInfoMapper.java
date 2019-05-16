package com.xia.sell.dao;

import com.xia.sell.po.SellerInfo;

import java.util.List;

public interface SellerInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SellerInfo record);

    int insertSelective(SellerInfo record);

    SellerInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SellerInfo record);

    int updateByPrimaryKey(SellerInfo record);

	SellerInfo selectByUsername(String username);

	List<SellerInfo> getAllSellerInfo();

	List<SellerInfo> getSellerInfoByCategory(String category);

	List<SellerInfo> getSellerInfoByCategoryOrderSold(String category);

	List<SellerInfo> getAllSellerInfoOrderSold();

	//List<SellerInfo> selectAllSellerInfo();
}