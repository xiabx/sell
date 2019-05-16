package com.xia.sell.controller;

import com.xia.sell.po.ProductCategory;
import com.xia.sell.service.SellerCategoryService;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.vo.ResultVO;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/seller/cate")
public class SellerCategoryController {
	@Autowired
	private SellerCategoryService categoryService;
	@GetMapping("/list")
	public String list(HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		String sellerId = (String) session.getAttribute("sellerId");
		List<ProductCategory> list = categoryService.list(sellerId);
		model.addAttribute("list", list);
		return "category";
	}
	@GetMapping("/change")
	@ResponseBody
	public ResultVO change(Integer categoryId,String newName){
		try {
			categoryService.change(categoryId,newName);
			return ResultVOUtil.success();
		}catch (Exception e){
			e.printStackTrace();
			return ResultVOUtil.error(1, "失败，未知错误");
		}
	}
	@GetMapping("/add")
	@ResponseBody
	public ResultVO add(String newName,HttpServletRequest request){
		try {
			HttpSession session = request.getSession();
			String sellerId = (String) session.getAttribute("sellerId");
			categoryService.add(newName,sellerId);
			return ResultVOUtil.success();
		}catch (Exception e){
			e.printStackTrace();
			return ResultVOUtil.error(1, "失败，未知错误");
		}
	}
	@GetMapping("/remove")
	@ResponseBody
	public ResultVO remove(Integer cateId){
		try {
			categoryService.remove(cateId);
			return ResultVOUtil.success();
		}catch (Exception e){
			e.printStackTrace();
			return ResultVOUtil.error(1, "败了");
		}

	}
}
