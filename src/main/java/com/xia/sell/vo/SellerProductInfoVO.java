package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerProductInfoVO {
	private String productId;

	private String productName;

	private BigDecimal productPrice;

	private Integer sold;

	private String productDescription;

	private String productIcon;

	private String productStatus;

	private String categoryType;

	private String createTime;

	private String updateTime;
}
