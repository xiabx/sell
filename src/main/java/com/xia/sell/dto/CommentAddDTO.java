package com.xia.sell.dto;

import lombok.Data;

@Data
public class CommentAddDTO {
	//private String sellerId;
	private String sessionId;
	private String orderId;
	private String content;
	private Integer star;
	private String buyerName;
	private String buyerIcon;
}
