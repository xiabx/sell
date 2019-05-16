package com.xia.sell.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xia.sell.dao.OrderCommentMapper;
import com.xia.sell.dao.OrderMasterMapper;
import com.xia.sell.dto.CommentAddDTO;
import com.xia.sell.enums.OrderStatusEnum;
import com.xia.sell.po.OrderComment;
import com.xia.sell.po.OrderMaster;
import com.xia.sell.service.BuyerCommentService;
import com.xia.sell.service.WechatSessionService;
import com.xia.sell.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuyerCommentServiceImpl implements BuyerCommentService {
	@Autowired
	private OrderCommentMapper orderCommentMapper;
	@Autowired
	private OrderMasterMapper orderMasterMapper;
	@Autowired
	private WechatSessionService wechatSessionService;
	@Override
	//对订单进行评价，并修改订单状态
	public boolean addComment(CommentAddDTO commentAddDTO) {
		OrderComment comment = new OrderComment();
		BeanUtils.copyProperties(commentAddDTO, comment);
		comment.setBuyerOpenid(wechatSessionService.getOpenid(commentAddDTO.getSessionId()));
		comment.setSellerId(orderMasterMapper.selectByPrimaryKey(commentAddDTO.getOrderId()).getSellerId());
		comment.setCommentId(KeyUtil.getCommentKey());
		orderCommentMapper.insertSelective(comment);
		OrderMaster orderMaster = new OrderMaster();
		orderMaster.setOrderId(commentAddDTO.getOrderId());
		orderMaster.setOrderStatus(OrderStatusEnum.COMMENT_FINISH.getCode());
		orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
		return true;
	}
	//查询一个商店的评价列表
	@Override
	public Map<String,Object> list(String sellerId, Integer page) {
		PageHelper.startPage(page, 10);
		List<OrderComment> orderComments = orderCommentMapper.selectBySellerId(sellerId);
		PageInfo<OrderComment> orderCommentPageInfo = new PageInfo<>(orderComments);
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", orderComments);
		map.put("isLastPage", orderCommentPageInfo.isIsLastPage());
		return map;
	}
}
