package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrderVO {
	private String orderId;

	private String buyerName;

	private String buyerPhone;

	private String location;

	private String detail;

	private BigDecimal orderAmount;

	private String orderStatus;

	private String note;

	//private String payStatus;

	private String createTime;



}
