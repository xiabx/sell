package com.xia.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerAddressDTO {

	private String addressId;
	private String sessionId;
	private String latitude;

	private String longitude;

	private String location;
	private String detail;

	private String buyerName;

	private String buyerPhone;
}
