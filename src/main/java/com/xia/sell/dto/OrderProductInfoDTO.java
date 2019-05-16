package com.xia.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单商品信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductInfoDTO {
	private String productId;
	private Integer productQuantity;

}
