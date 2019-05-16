package com.xia.sell.service;

import java.util.List;

public interface SellerCategoryService {
	List list(String sellerId);
	void change(Integer categoryType,String newName);

	void add(String newName,String sellerId);

	void remove(Integer cateId);
}
