package com.xia.sell.util;

import com.xia.sell.enums.EnumCode;

public class EnumUtil {

	public static String getMessage(Byte code,Class<? extends EnumCode> aClass){
		EnumCode[] enumConstants = aClass.getEnumConstants();
		for (EnumCode enumConstant : enumConstants) {
			if (code.equals(enumConstant.getCode())){
				return enumConstant.getMessage();
			}
		}
		return "";
	}
}
