package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopVo implements Comparator {
	private String id;
	private String shopName;
	private Integer beginFee;
	private Integer distributionFee;
	private String  shopIcon;
	private Double distance;
	private Integer shopSold;
	private String longitude;
	private String latitude;


	@Override
	public int compare(Object o1, Object o2) {
		ShopVo vo1 = (ShopVo) o1;
		ShopVo vo2 = (ShopVo) o2;

		if (vo1.getDistance()>vo2.getDistance()){
			return 1;
		}
		if (vo1.getDistance()<vo2.getDistance()){
			return -1;
		}
		if (vo1.getDistance() == vo2.getDistance()){
			return 0;
		}
		return 0;
	}
}
