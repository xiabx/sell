package com.xia.sell.service.impl;

import com.xia.sell.dao.ProductCategoryMapper;
import com.xia.sell.dao.ProductInfoMapper;
import com.xia.sell.enums.RemoveStatusEnum;
import com.xia.sell.po.ProductCategory;
import com.xia.sell.service.SellerCategoryService;
import com.xia.sell.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SellerCategoryServiceImpl implements SellerCategoryService {
	@Autowired
	private ProductCategoryMapper categoryMapper;
	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Override
	public List list(String sellerId) {
		List<ProductCategory> categories = categoryMapper.selectCateBySellerId(sellerId);
		return categories;
	}

	@Override
	public void change(Integer categoryId, String newName) {
		ProductCategory cate = new ProductCategory();
		cate.setCategoryId(categoryId);
		cate.setCategoryName(newName);
		categoryMapper.updateByPrimaryKeySelective(cate);
	}

	@Override
	public void add(String newName,String sellerId) {
		ProductCategory category = new ProductCategory();
		category.setCategoryName(newName);
		category.setSellerId(sellerId);
		category.setCategoryType(KeyUtil.getCategoryType());
		categoryMapper.insertSelective(category);
	}

	@Override
	public void remove(Integer cateId) {
		ProductCategory ca = new ProductCategory();
		ca.setCategoryId(cateId);
		ca.setRemove(RemoveStatusEnum.remove.getCode());
		String categoryType = categoryMapper.selectByPrimaryKey(cateId).getCategoryType();
		productInfoMapper.setRemoveByCategoryType(categoryType);
		categoryMapper.updateByPrimaryKeySelective(ca);
	}
}
