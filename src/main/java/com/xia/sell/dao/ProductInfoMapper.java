package com.xia.sell.dao;

import com.xia.sell.po.ProductInfo;
import com.xia.sell.vo.SellerProductInfoVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(String productId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    List<ProductInfo> selectAllProductInfosForPages();

    Set<String> selectAllProductCategoryTypeByShopId(String sellerId);

    List<ProductInfo> selectProductInfoByProductCategory(Map map);

	List<ProductInfo> selectAllProduct();

    List<SellerProductInfoVO> selectSellerProductInfoVO(String sellerId);

    void setRemoveByCategoryType(String categoryType);
}