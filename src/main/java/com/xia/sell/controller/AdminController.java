package com.xia.sell.controller;

import com.xia.sell.po.SellerInfo;
import com.xia.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private SellerInfoService sellerInfoService;
	@GetMapping("/admin")
	public String admin(){
		return "admin";
	}
	@GetMapping("/sellers")
	@ResponseBody
	public Object allSeller(){
		List<SellerInfo> sellerInfos = sellerInfoService.selectAllSeller();

		return sellerInfos;
	}
}
