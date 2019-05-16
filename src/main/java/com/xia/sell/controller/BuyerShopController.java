package com.xia.sell.controller;

import com.xia.sell.service.BuyerShopService;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.vo.BuyerShopSimpleDetailVo;
import com.xia.sell.vo.ResultVO;
import com.xia.sell.vo.SellerDiscountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buyer/shop")
public class BuyerShopController {
	@Autowired
	private BuyerShopService buyerShopService;
	//买家附近 商店列表 默认
	@GetMapping("/list")
	public ResultVO<List> allShop(String longitude,String latitude){
		List shopList = buyerShopService.getShopList(longitude, latitude);
		ResultVO success = ResultVOUtil.success(shopList);
		return success;
	}
	//买家附近 商店列表  距离
	@GetMapping("/listDistance")
	public ResultVO<List> allShopOrderDistance(String longitude,String latitude){
		List shopList = buyerShopService.getShopListOrderDistance(longitude, latitude);
		ResultVO success = ResultVOUtil.success(shopList);
		return success;
	}
	//买家附近 商店列表 销量
	@GetMapping("/listSold")
	public ResultVO<List> allShopOrderSold(String longitude,String latitude){
		List shopList = buyerShopService.getShopListOrderSold(longitude, latitude);
		ResultVO success = ResultVOUtil.success(shopList);
		return success;
	}
	//商家分类 销量 排序
	@GetMapping("/listOrderSold")
	public ResultVO listOrderSold(String longitude,String latitude,String category){
		List list = buyerShopService.listOrderSold(longitude, latitude, category);
		return ResultVOUtil.success(list);
	}
	//商家分类 距离 排序
	@GetMapping("/listOrderDistance")
	public ResultVO listOrderDistance(String longitude,String latitude,String category){
		List list = buyerShopService.listOrderDistance(longitude, latitude, category);
		return ResultVOUtil.success(list);
	}

	//商家分类 默认 排序
	@GetMapping("/listDefault")
	public ResultVO listDefault(String longitude,String latitude,String category){
		List list = buyerShopService.listDefault(longitude, latitude, category);
		return ResultVOUtil.success(list);
	}

	//简略信息。用来product界面显示商家信息
	@GetMapping("/simpleDetail")
	public BuyerShopSimpleDetailVo simpleDetail(String sellerId){
		BuyerShopSimpleDetailVo detailVo = buyerShopService.simpleDetail(sellerId);
		return detailVo;
	}
	@GetMapping("/discount")
	public ResultVO sellerDiscount(String sellerId){
		List<SellerDiscountVO> list = buyerShopService.sellerDiscount(sellerId);
		ResultVO success = ResultVOUtil.success(list);
		return success;
	}
}
