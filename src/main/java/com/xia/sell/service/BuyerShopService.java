package com.xia.sell.service;

import com.xia.sell.vo.BuyerShopSimpleDetailVo;
import com.xia.sell.vo.SellerDiscountVO;

import java.util.List;

public interface BuyerShopService {
	List getShopList(String longitude,String latitude);

	List getShopListOrderSold(String longitude,String latitude);

	List getShopListOrderDistance(String longitude,String latitude);

	BuyerShopSimpleDetailVo simpleDetail(String sellerId);

	List<SellerDiscountVO> sellerDiscount(String sellerId);

	List listOrderSold(String longitude,String latitude,String category);

	List listOrderDistance(String longitude, String latitude, String category);

	List listDefault(String longitude, String latitude, String category);
}
