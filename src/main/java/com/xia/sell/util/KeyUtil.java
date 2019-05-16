package com.xia.sell.util;

public class KeyUtil {
	public static String getOrderMasterKey(){
		long l = System.currentTimeMillis();
		return "om"+l;
	}
	public static String getOrderDetailKey(){
		long l = System.currentTimeMillis();
		return "od"+l;
	}
	public static String getBuyerAddressKey(){
		long l = System.currentTimeMillis();
		return "ba"+l;
	}
	public static String getProductInfoKey(){
		long l = System.currentTimeMillis();
		return "pi"+l;
	}

	public static String getCommentKey() {
		long l = System.currentTimeMillis();
		return l+"ci";
	}

	public static String getProductIconKey() {
		long l = System.currentTimeMillis();
		return l+"pi.jpg";
	}

	public static String getCategoryType() {
		long l = System.currentTimeMillis();
		return l+"cate";
	}
}
