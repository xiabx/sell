package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单详情中商品信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailInfoVO {
	private String detailId;
	private String orderId;
	private String productId;
	private String productName;
	private BigDecimal productPrice;
	private Integer productQuantity;
	private String productIcon;
	private String productImage;

}
