package com.xia.sell.service;

import com.xia.sell.po.SellerInfo;

import java.util.List;

public interface SellerInfoService {
	SellerInfo selectSellerInfoByUsername(String sellerId);
	List<SellerInfo> selectAllSeller();
}
