package com.xia.sell.controller;

import com.xia.sell.service.ProductListService;
import com.xia.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer/product")
public class ProductListController {
	@Autowired
	private ProductListService productListService;
	@GetMapping("/list")
	public ResultVO productList(String sellerId){
		ResultVO productList = productListService.getAllProductList(sellerId);
		return productList;
	}
}
