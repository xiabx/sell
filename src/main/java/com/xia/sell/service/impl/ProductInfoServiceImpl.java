package com.xia.sell.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xia.sell.dao.ProductInfoMapper;
import com.xia.sell.po.ProductInfo;
import com.xia.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Override
	public List<ProductInfo> getAllProductInfoForPages(int page,int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<ProductInfo> allProductInfo = productInfoMapper.selectAllProductInfosForPages();
		PageInfo<ProductInfo> productInfosPage = new PageInfo<>(allProductInfo);
		return productInfosPage.getList();
	}
}
