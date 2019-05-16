package com.xia.sell.service.impl;

import com.xia.sell.dao.ProductCategoryMapper;
import com.xia.sell.po.ProductCategory;
import com.xia.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	@Override
	public void insertProductCategory(ProductCategory productCategory) {
		productCategoryMapper.insertSelective(productCategory);
	}
}
