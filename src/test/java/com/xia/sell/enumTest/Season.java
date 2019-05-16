package com.xia.sell.enumTest;

public enum Season {
	SPRING("chun"),SUMMER("xia"),FALL("qiu"),WINTER("dong");
	private final String name;

	Season(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
