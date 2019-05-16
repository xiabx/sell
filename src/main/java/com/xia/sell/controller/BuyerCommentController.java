package com.xia.sell.controller;

import com.xia.sell.dto.CommentAddDTO;
import com.xia.sell.service.BuyerCommentService;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer/comment")
public class BuyerCommentController {
	@Autowired
	private BuyerCommentService buyerCommentService;

	//添加评论
	@PostMapping("/add")
	public ResultVO add(@RequestBody CommentAddDTO addDTO){
		boolean isSure = buyerCommentService.addComment(addDTO);
		return ResultVOUtil.success(isSure);
	}
	//获取一个商店的评论列表
	@GetMapping("/list")
	public ResultVO list(String sellerId,Integer page){
		return ResultVOUtil.success(buyerCommentService.list(sellerId,page));
	}
}
