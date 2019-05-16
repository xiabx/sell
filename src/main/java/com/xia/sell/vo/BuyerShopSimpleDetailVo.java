package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerShopSimpleDetailVo {
	private String id;
	private String shopName;
	private String shopIcon;
	private Integer beginFee;
	private Integer distributionFee;
	private String longitude;
	private String latitude;

}
