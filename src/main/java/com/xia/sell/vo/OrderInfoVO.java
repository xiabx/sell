package com.xia.sell.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoVO {
	private String orderId;
	private String addressId;
	private String buyerOpenid;
	private BigDecimal orderAmount;
	private byte orderStatus;
	private byte payStatus;

	private String createTime;
	private String updateTime;


	private List<OrderDetailInfoVO> items;
}
