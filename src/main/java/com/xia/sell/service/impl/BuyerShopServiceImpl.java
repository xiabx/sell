package com.xia.sell.service.impl;

import com.xia.sell.dao.SellerDiscountMapper;
import com.xia.sell.dao.SellerInfoMapper;
import com.xia.sell.po.SellerDiscount;
import com.xia.sell.po.SellerInfo;
import com.xia.sell.service.BuyerShopService;
import com.xia.sell.util.Distance;
import com.xia.sell.vo.BuyerShopSimpleDetailVo;
import com.xia.sell.vo.SellerDiscountVO;
import com.xia.sell.vo.ShopVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class BuyerShopServiceImpl implements BuyerShopService {
	@Autowired
	private SellerInfoMapper sellerInfoMapper;
	@Autowired
	private SellerDiscountMapper sellerDiscountMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Override
	public List<ShopVo> getShopList(String longitude,String latitude) {
		//List<SellerInfo> sellerInfos = sellerInfoMapper.getAllSellerInfo();
		//List<ShopVo> shopVos = setDistance(sellerInfos,longitude,latitude);

		GeoOperations geo = redisTemplate.opsForGeo();
		ValueOperations opsForValue = redisTemplate.opsForValue();
		Circle circle = new Circle(Double.parseDouble(longitude), Double.parseDouble(latitude), 10000);
		RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending();
		GeoResults<RedisGeoCommands.GeoLocation<String>> results = geo.radius("shops",circle,args);
		ArrayList<ShopVo> shopVos = new ArrayList<>();
		for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
			ShopVo shopVo = new ShopVo();
			String name = result.getContent().getName();
			double distance = result.getDistance().getValue();
			SellerInfo sellerInfo = (SellerInfo) opsForValue.get(name);
			shopVo.setDistance(distance);
			BeanUtils.copyProperties(sellerInfo,shopVo);
			shopVos.add(shopVo);
		}

		return shopVos;
	}

	@Override
	public List getShopListOrderSold(String longitude, String latitude) {
		List<SellerInfo> sellerInfos = sellerInfoMapper.getAllSellerInfoOrderSold();
		List<ShopVo> shopVos = setDistance(sellerInfos,longitude,latitude);

		return shopVos;
	}

	@Override
	public List getShopListOrderDistance(String longitude, String latitude) {
		List<SellerInfo> sellerInfos = sellerInfoMapper.getAllSellerInfo();
		List<ShopVo> shopVos = setDistance(sellerInfos,longitude,latitude);
		Collections.sort(shopVos, new ShopVo());
		return shopVos;
	}

	@Override
	public BuyerShopSimpleDetailVo simpleDetail(String sellerId) {
		SellerInfo sellerInfo = sellerInfoMapper.selectByPrimaryKey(sellerId);
		BuyerShopSimpleDetailVo detailVo = new BuyerShopSimpleDetailVo();
		BeanUtils.copyProperties(sellerInfo, detailVo);
		return detailVo;
	}

	@Override
	public List<SellerDiscountVO> sellerDiscount(String sellerId) {
		List<SellerDiscount> sellerDiscounts = sellerDiscountMapper.selectBySellerId(sellerId);
		ArrayList<SellerDiscountVO> vos = new ArrayList<>();
		for (SellerDiscount sellerDiscount : sellerDiscounts) {
			SellerDiscountVO vo = new SellerDiscountVO();
			BeanUtils.copyProperties(sellerDiscount, vo);
			vos.add(vo);
		}
		return vos;
	}
	//计算距离 筛选距离小于10000米的店
	public  List setDistance(List<SellerInfo> list,String longitude,String latitude){
		ArrayList<ShopVo> shopVos = new ArrayList<>();
		for (SellerInfo sellerInfo : list) {
			double distance = Distance.getDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), Double.parseDouble(sellerInfo.getLongitude()), Double.parseDouble(sellerInfo.getLatitude()));
			if (distance < 10000){
				ShopVo shopVo = new ShopVo();
				BeanUtils.copyProperties(sellerInfo,shopVo);
				shopVo.setDistance(distance);
				shopVos.add(shopVo);
			}
		}
		return shopVos;
	}

	@Override
	public List listOrderSold(String longitude, String latitude, String category) {
		List<SellerInfo> infos = sellerInfoMapper.getSellerInfoByCategoryOrderSold(category);
		List<ShopVo> shopVos = setDistance(infos,longitude,latitude);
		return shopVos;
	}

	@Override
	public List listOrderDistance(String longitude, String latitude, String category) {
		List<SellerInfo> list = sellerInfoMapper.getSellerInfoByCategory(category);
		List list1 = setDistance(list, longitude, latitude);
		Collections.sort(list1, new ShopVo());
		return list1;
	}

	@Override
	public List listDefault(String longitude, String latitude, String category) {
		List<SellerInfo> sellerInfoByCategory = sellerInfoMapper.getSellerInfoByCategory(category);
		List list = setDistance(sellerInfoByCategory, longitude, latitude);
		return list;
	}
}
