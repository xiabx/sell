package com.xia.sell.enums;

public enum ResultEnum {
	PAY_SUCCESS(1,"支付成功"),
	ORDER_CANCEL_SUCCESS(2,"订单取消成功"),
	ORDER_FINISH_SUCCESS(3,"订单完结成功"),
	PRODUCT_NOT_EXIST(10, "商品不存在"),
	PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
	ORDER_NOT_EXIST(12, "订单不存在"),
	ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),
	ORDERSTATUS_NOT_NEW(14, "订单状态异常"),
	ORDER_FORM_NOT_FULL(15, "数据不完整"),
	QUERY_PARM_NOT_FULL(16, "参数不合法"),
	MORE_THAN_DISTANCE(17, "超出配送范围"),
	ALREADY_ORDER_SUCCESS(18, "超出配送范围"),
	;
	private Integer code;
	private String message;

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
