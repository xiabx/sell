package com.xia.sell.exception;

import com.xia.sell.enums.ResultEnum;

public class SellException extends RuntimeException {
	private Integer code;

	public SellException(ResultEnum resultEnum,String productId) {
		super(resultEnum.getMessage()+":   "+productId);
		this.code=resultEnum.getCode();
	}
	public SellException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.code=resultEnum.getCode();
	}
}
