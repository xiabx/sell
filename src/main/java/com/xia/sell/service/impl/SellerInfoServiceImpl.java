package com.xia.sell.service.impl;

import com.xia.sell.dao.SellerInfoMapper;
import com.xia.sell.po.SellerInfo;
import com.xia.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerInfoServiceImpl implements SellerInfoService{
	@Autowired
	private SellerInfoMapper sellerInfoMapper;
	@Override
	public SellerInfo selectSellerInfoByUsername(String sellerId) {
		SellerInfo sellerInfo = sellerInfoMapper.selectByPrimaryKey(sellerId);
		return sellerInfo;
	}

	@Override
	public List<SellerInfo> selectAllSeller() {
		List<SellerInfo> list = sellerInfoMapper.getAllSellerInfo();
		return list;
	}
}
