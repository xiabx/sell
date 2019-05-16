package com.xia.sell.dao;

import com.xia.sell.po.ProductCategory;

import java.util.List;

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

	ProductCategory selectCategoryByType(String categoryType);

	List<ProductCategory> selectAllCategory();

	List<ProductCategory> selectCateBySellerId(String sellerId);
}