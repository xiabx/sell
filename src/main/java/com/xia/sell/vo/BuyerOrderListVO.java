package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerOrderListVO {
	private String orderId;
	private String shopName;
	private String createTime;
	private String orderStatus;
	private String productName;
	private String shopIcon;
	private String amount;
}
