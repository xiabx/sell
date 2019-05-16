package com.xia.sell.enums;

import lombok.Getter;

/**
 * 订单状态
 */
@Getter
public enum OrderStatusEnum implements EnumCode {
	TO_PAY((byte)0, "待支付"),
	ALREADY_PAY((byte)1, "已支付，等待商家接单"),
	ALREADY_ORDER((byte)2, "商家已接单，待配送"),
	DISTRIBUTIONING((byte)6, "订单配送中"),
	WAITE_COMMENT((byte)3, "配送完成，待评价"),
	COMMENT_FINISH((byte)4, "订单完成"),
	CANCEL((byte)5, "已取消"),
	;

	private Byte code;

	private String message;

	OrderStatusEnum(Byte code, String message) {
		this.code = code;
		this.message = message;
	}
}
