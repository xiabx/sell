package com.xia.sell.controller;

import com.xia.sell.config.InitRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@Autowired
	private InitRedis initRedis;
	@GetMapping("/")
	public String index(){
		//initRedis.init();
		return "redirect:/seller/order/list";
	}
	@GetMapping("/initRedis")
	@ResponseBody
	public String initRedis(){
		initRedis.init();
		return "ok";
	}

}
