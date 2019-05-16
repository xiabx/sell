package com.xia.sell.config;

import com.xia.sell.po.SellerInfo;
import com.xia.sell.service.SellerInfoService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
@Component
@Log
public class InitRedis {
	@Autowired
	private  RedisTemplate redisTemplate;
	@Autowired
	private SellerInfoService infoService;

	public void init(){
		ValueOperations<String,SellerInfo> opsForValue = redisTemplate.opsForValue();
		GeoOperations geo = redisTemplate.opsForGeo();
		List<SellerInfo> list = infoService.selectAllSeller();
		HashMap<String, Point> map = new HashMap<>();
		for (SellerInfo sellerInfo : list) {
			map.put(sellerInfo.getId(), new Point(Double.parseDouble(sellerInfo.getLongitude()),Double.parseDouble(sellerInfo.getLatitude())));
			opsForValue.set(sellerInfo.getId(), sellerInfo);
		}
		geo.add("shops",map);
		log.info("redis init success");
	}

}
