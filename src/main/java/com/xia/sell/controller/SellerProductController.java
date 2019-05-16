package com.xia.sell.controller;

import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.xia.sell.dto.ProductInfoDTO;
import com.xia.sell.exception.SellException;
import com.xia.sell.po.ProductCategory;
import com.xia.sell.po.ProductInfo;
import com.xia.sell.service.SellerProductService;
import com.xia.sell.util.KeyUtil;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.vo.ResultVO;
import com.xia.sell.vo.SellerProductInfoVO;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
	@Autowired
	private SellerProductService sellerProductService;
	@Autowired
	private BosClient client;
	@GetMapping("/list")
	public String list(Model model,HttpServletRequest request){
		HttpSession session = request.getSession();
		String sellerId = (String) session.getAttribute("sellerId");
		List<SellerProductInfoVO> sellerProductInfoVOS = sellerProductService.productList(sellerId);
		model.addAttribute("sellerProductInfoVOS", sellerProductInfoVOS);
		return "productList";
	}
	@GetMapping("/changeStatus")
	@ResponseBody
	public ResultVO changeStatus(String productId){
		try {
			sellerProductService.changeStatus(productId);
			return ResultVOUtil.success();
		}catch (SellException e){
			return ResultVOUtil.error(1, e.getMessage());
		}
	}
	@GetMapping("/addProduct")
	public String getAddProductPage(Model model,HttpServletRequest request){
		HttpSession session = request.getSession();
		String sellerId = (String) session.getAttribute("sellerId");
		List<ProductCategory> productCategories = sellerProductService.categoryList(sellerId);
		model.addAttribute("productCategories", productCategories);
		return "addProduct";
	}
	@GetMapping("/remove")
	@ResponseBody
	public ResultVO remove(String productId){
		try {
			sellerProductService.remove(productId);
			return ResultVOUtil.success();
		}catch (Exception e){
			return ResultVOUtil.error(1, "太懒了，不想处理异常了。。。");
		}
	}
	@GetMapping("/oneProduct")
	@ResponseBody
	public ResultVO getChangeProduct(String productId,HttpServletRequest request){
		try {
			HttpSession session = request.getSession();
			String sellerId = (String) session.getAttribute("sellerId");
			ProductInfo productInfo = sellerProductService.selectProductInfoByPrimary(productId);
			List<ProductCategory> productCategories = sellerProductService.categoryList(sellerId);
			HashMap<String, Object> map = new HashMap<>();
			map.put("info", productInfo);
			map.put("cate", productCategories);
			return ResultVOUtil.success(map);
		}catch (SellException e){
			return ResultVOUtil.error(1, e.getMessage());
		}
	}
	@PostMapping("/changeProduct")
	@ResponseBody
	public ResultVO changeProduct(@Valid ProductInfoDTO productInfoDTO, Errors errors) {
			if (errors.hasErrors()){
				List<ObjectError> allErrors = errors.getAllErrors();
				String msg = allErrors.get(0).getDefaultMessage();
				return ResultVOUtil.error(1, msg);
			}
		try {
			// 图片变化了
			if (productInfoDTO.getProductIcon().getSize() != 0){
				InputStream inputStream = productInfoDTO.getProductIcon().getInputStream();
				String productIconKey = KeyUtil.getProductIconKey();
				PutObjectResponse putObjectResponseFromInputStream = client.putObject("waimai1996", productIconKey, inputStream);
				URL url = client.generatePresignedUrl("waimai1996",productIconKey, -1);
				productInfoDTO.setProductIcon2(url.toString());
			}
			sellerProductService.changeProduct(productInfoDTO);
			return ResultVOUtil.success();
		}catch (Exception e){
			return ResultVOUtil.error(1, "未知错误，请稍后再试");
		}
	}

	@PostMapping("/addProduct")
	@ResponseBody
	public ResultVO addProduct(@Valid ProductInfoDTO productInfoDTO, Errors errors,HttpServletRequest request){
		if (errors.hasErrors()){
			List<ObjectError> allErrors = errors.getAllErrors();
			String msg = allErrors.get(0).getDefaultMessage();
			return ResultVOUtil.error(1, msg);
		}
		try {
			HttpSession session = request.getSession();
			String sellerId = (String) session.getAttribute("sellerId");
			if (productInfoDTO.getProductIcon().getSize() != 0){
				InputStream inputStream = productInfoDTO.getProductIcon().getInputStream();
				String productIconKey = KeyUtil.getProductIconKey();
				PutObjectResponse putObjectResponseFromInputStream = client.putObject("waimai1996", productIconKey, inputStream);
				URL url = client.generatePresignedUrl("waimai1996",productIconKey, -1);
				productInfoDTO.setProductIcon2(url.toString());
			}else {
				//设置默认图片
				productInfoDTO.setProductIcon2("http://waimai1996.bj.bcebos.com/sorry.png?authorization=bce-auth-v1/0b8f90ee057342ce960257df33e0ec7b/2018-10-11T06:29:21Z/-1/host/f5f83ec3ab80fa877f4eeb6c5b11789f265f50e496530258fdcd9c710bc50fdb");
			}
			sellerProductService.addProduct(productInfoDTO,sellerId);
			return ResultVOUtil.success();
		}catch (Exception e){
			e.printStackTrace();
			return ResultVOUtil.error(1, "未知错误，请稍后再试");
		}
	}
}
