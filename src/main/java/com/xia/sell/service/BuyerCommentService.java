package com.xia.sell.service;

import com.xia.sell.dto.CommentAddDTO;

import java.util.Map;

public interface BuyerCommentService {

	boolean addComment(CommentAddDTO commentAddDTO);

	Map<String,Object> list(String sellerId, Integer page);
}
