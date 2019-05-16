package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerAddressVO {
	private String addressId;

	private String latitude;

	private String longitude;
	//地址的文字描述
	private String location;

	private String detail;

	private String buyerName;

	private String buyerPhone;
}
