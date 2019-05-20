package com.xia.sell.service.impl;

import com.xia.sell.dao.ProductCategoryMapper;
import com.xia.sell.dao.ProductInfoMapper;
import com.xia.sell.dto.ProductInfoDTO;
import com.xia.sell.enums.ProductStatusEnum;
import com.xia.sell.enums.RemoveStatusEnum;
import com.xia.sell.enums.ResultEnum;
import com.xia.sell.exception.SellException;
import com.xia.sell.po.ProductCategory;
import com.xia.sell.po.ProductInfo;
import com.xia.sell.service.SellerProductService;
import com.xia.sell.util.EnumUtil;
import com.xia.sell.util.KeyUtil;
import com.xia.sell.vo.SellerProductInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service
public class SellerProductServiceImpl implements SellerProductService {
	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ProductCategoryMapper categoryMapper;
	@Override
	public Object productList(Integer page,String sellerId) {
		//PageHelper.startPage(page, 6);
		List<SellerProductInfoVO> sellerProductInfoVOS = productInfoMapper.selectSellerProductInfoVO(sellerId);
		//PageInfo<SellerProductInfoVO> pages = new PageInfo<>(sellerProductInfoVOS);
		for (SellerProductInfoVO vo : sellerProductInfoVOS) {
			vo.setProductStatus(EnumUtil.getMessage(Byte.valueOf(vo.getProductStatus()), ProductStatusEnum.class));
		}
		HashMap<String, Object> map = new HashMap<>();
		//map.put("page", pages);
		map.put("plist", sellerProductInfoVOS);
		return map;
	}

	@Override
	public void changeStatus(String productId) {
		ProductInfo productInfo = selectProductInfoByPrimary(productId);
		if (productInfo==null){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}

		if (productInfo.getProductStatus() == 0){
			productInfo = new ProductInfo();
			productInfo.setProductId(productId);
			productInfo.setProductStatus((byte) 1);
		}else {
			productInfo = new ProductInfo();
			productInfo.setProductId(productId);
			productInfo.setProductStatus((byte) 0);
		}
		productInfoMapper.updateByPrimaryKeySelective(productInfo);
	}

	@Override
	public void addProduct(ProductInfoDTO productInfoDTO,String sellerId) {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setSellerId(sellerId);
		BeanUtils.copyProperties(productInfoDTO, productInfo);
		productInfo.setProductIcon(productInfoDTO.getProductIcon2());
		productInfo.setProductStatus(productInfoDTO.getProductStatus().byteValue());
		productInfo.setProductId(KeyUtil.getProductInfoKey());
		productInfoMapper.insertSelective(productInfo);
	}

	@Override
	public List<ProductCategory> categoryList(String sellerId) {
		List<ProductCategory> productCategories = categoryMapper.selectCateBySellerId(sellerId);
		return productCategories;
	}

	@Override
	public void changeProduct(ProductInfoDTO productInfoDTO) {
		ProductInfo productInfo = new ProductInfo();
		BeanUtils.copyProperties(productInfoDTO, productInfo);
		productInfo.setProductIcon(productInfoDTO.getProductIcon2());
		productInfo.setProductStatus(productInfoDTO.getProductStatus().byteValue());
		productInfoMapper.updateByPrimaryKeySelective(productInfo);
	}

	@Override
	public ProductInfo selectProductInfoByPrimary(String productId) {
		ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
		if (productId == null){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		return productInfo;
	}

	@Override
	public void remove(String productId) {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId(productId);
		productInfo.setRemove(RemoveStatusEnum.remove.getCode());
		productInfoMapper.updateByPrimaryKeySelective(productInfo);
	}
}
