package com.xia.sell.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckTimeOut {


	public static boolean isTimeOut(String createTime){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parse = format.parse(createTime);
			if (System.currentTimeMillis()- parse.getTime() >= 900000){
				return true;
			}else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getMinute(String createTime){

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parse = format.parse(createTime);
			long l = 900000 - (System.currentTimeMillis() - parse.getTime());
			long second = l/1000;
			int minute = (int) (second/60);
			if (minute<10){
				return "0"+minute;
			}
			return minute+"";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "00";
	}

	public static String getSecond(String createTime){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parse = format.parse(createTime);
			long l = 900000 - (System.currentTimeMillis() - parse.getTime());
			long seconds = l/1000;
			int second = (int) (seconds % 60);
			if (second<10){
				return "0"+second;
			}
			return second+"";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "00";
	}
}
