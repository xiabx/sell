package com.xia.sell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
/**
 * 商品 商品分类删除
 */
public enum RemoveStatusEnum {
	noRemove(0,"未删除"),
	remove(1,"已删除");

	private Integer code;
	private String message;
}
