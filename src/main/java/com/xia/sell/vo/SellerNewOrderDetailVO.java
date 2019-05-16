package com.xia.sell.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellerNewOrderDetailVO {
	private String productId;
	private String productName;
	private String productPrice;
	private Integer productQuantity;
	private BigDecimal amount;
	private BigDecimal itemAmount;


}
