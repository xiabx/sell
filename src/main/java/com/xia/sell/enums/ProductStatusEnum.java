package com.xia.sell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProductStatusEnum implements EnumCode {

	offSell((byte)1,"已下架"),
	onSell((byte)0,"上架中");


	private Byte code;
	private String message;

}
