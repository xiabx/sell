package com.xia.sell.service.impl;

import com.xia.sell.dao.ProductCategoryMapper;
import com.xia.sell.dao.ProductInfoMapper;
import com.xia.sell.dao.SellerInfoMapper;
import com.xia.sell.po.ProductCategory;
import com.xia.sell.po.ProductInfo;
import com.xia.sell.service.ProductListService;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.vo.BuyerProductCategoryVO;
import com.xia.sell.vo.ProductInfoVO;
import com.xia.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 全部商品
 */
@Service
public class ProductListServiceImpl implements ProductListService {
	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	@Autowired
	private SellerInfoMapper sellerInfoMapper;

	@Override
	//获取所有在售商品
	public ResultVO getAllProductList(String sellerId) {
		//获取当前商店的所有分类
		Set<String> categoryTypes = productInfoMapper.selectAllProductCategoryTypeByShopId(sellerId);
		//每个分类下的商品
		ArrayList<BuyerProductCategoryVO> productVOS = new ArrayList<>();
		for (String categoryType : categoryTypes) {
			//根据categoryType获取ProductVO
			ProductCategory productCategory = productCategoryMapper.selectCategoryByType(categoryType);
			BuyerProductCategoryVO categoryVO = new BuyerProductCategoryVO();
			BeanUtils.copyProperties(productCategory,categoryVO);
			//根据类型获取该类型 该卖家id 下的所有商品
			HashMap<String, String> map = new HashMap<>();
			map.put("categoryType", categoryType);
			map.put("sellerId", sellerId);
			List<ProductInfo> infos = productInfoMapper.selectProductInfoByProductCategory(map);
			ArrayList<ProductInfoVO> productInfoVOS = new ArrayList<>();
			for (ProductInfo info : infos) {
				ProductInfoVO productInfoVO = new ProductInfoVO();
				BeanUtils.copyProperties(info, productInfoVO);
				productInfoVOS.add(productInfoVO);
			}
			categoryVO.setFoods(productInfoVOS);
			productVOS.add(categoryVO);
		}
		ResultVO success = ResultVOUtil.success(productVOS);
		return success;
	}
}
