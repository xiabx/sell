package com.xia.sell.enumTest;

public class SeasonTest {
	public void judge(Season season){
		switch (season){
			case SPRING:
				System.out.println("spring");
				break;
			case SUMMER:
				System.out.println("SUMMER");
				break;
			case FALL:
				System.out.println("FALL");
				break;
			case WINTER:
				System.out.println("WINTER");
				break;
		}
	}

	public static void main(String[] args) {
		for (Season season : Season.values()) {
			System.out.println(season);
		}
		System.out.println("-----------------");
		new SeasonTest().judge(Season.SUMMER);
		System.out.println("-----------------");
		System.out.println(Season.FALL.toString());
		System.out.println(Season.FALL.name());
		System.out.println("------------------");
		System.out.println(Season.SPRING.getName());
	}
}
