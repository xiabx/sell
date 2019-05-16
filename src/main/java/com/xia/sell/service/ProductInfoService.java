package com.xia.sell.service;

import com.xia.sell.po.ProductInfo;

import java.util.List;

public interface ProductInfoService {

	List<ProductInfo> getAllProductInfoForPages(int page,int pageSize);

}
