package com.xia.sell.converter;

import com.xia.sell.enums.ProductStatusEnum;
import com.xia.sell.po.ProductInfo;
import com.xia.sell.util.EnumUtil;
import com.xia.sell.vo.SellerProductInfoVO;
import org.springframework.beans.BeanUtils;

public class ProductInfo2SellerProductInfoVO {

	public static SellerProductInfoVO toConverter(ProductInfo productInfo){
		SellerProductInfoVO sellerProductInfoVO = new SellerProductInfoVO();
		BeanUtils.copyProperties(productInfo, sellerProductInfoVO);
		sellerProductInfoVO.setProductStatus(EnumUtil.getMessage(productInfo.getProductStatus(), ProductStatusEnum.class));
		return sellerProductInfoVO;
	}
}
