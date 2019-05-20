package com.xia.sell.service;

import com.xia.sell.dto.ProductInfoDTO;
import com.xia.sell.po.ProductCategory;
import com.xia.sell.po.ProductInfo;

import java.util.List;

public interface SellerProductService {

	Object productList(Integer page,String sellerId);

	void changeStatus(String productId);

	void addProduct(ProductInfoDTO productInfoDTO,String sellerId);

	List<ProductCategory> categoryList(String sellerId);

	void changeProduct(ProductInfoDTO productInfoDTO);

	ProductInfo selectProductInfoByPrimary(String productId);

	void remove(String productId);
}
