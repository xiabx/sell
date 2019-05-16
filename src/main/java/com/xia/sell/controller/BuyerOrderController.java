package com.xia.sell.controller;

import com.xia.sell.dto.OrderCreateDTO;
import com.xia.sell.enums.ResultEnum;
import com.xia.sell.exception.SellException;
import com.xia.sell.service.OrderWebSocket;
import com.xia.sell.service.WechatSessionService;
import com.xia.sell.service.impl.OrderServiceImpl;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.vo.BuyerOrderDetailVO;
import com.xia.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
	@Autowired
	private OrderServiceImpl orderService;
	@Autowired
	private OrderWebSocket orderWebSocket;
	@Autowired
	private WechatSessionService wechatSessionService;
	//创建订单
	@PostMapping("/create")
	public ResultVO create(@RequestBody @Valid OrderCreateDTO orderCreateDTO, Errors errors){
		if(errors.hasErrors()){
			throw new SellException(ResultEnum.ORDER_FORM_NOT_FULL,errors.getFieldError().getDefaultMessage());
		}
		try {
			Map<String, Object> newOrder = orderService.createOrder(orderCreateDTO);
			//HashMap<String, Object> map = new HashMap<>();
			//map.put("orderId", newOrder.get("orderId"));
			//String s = com.alibaba.fastjson.JSON.toJSONString(newOrder);
			//orderWebSocket.sendMessage(s);
			return ResultVOUtil.success(newOrder);
		}catch (SellException e){
			return new ResultVOUtil().error_Enum(ResultEnum.MORE_THAN_DISTANCE);
		}
	}
	/** 返回订单详情 */
	@GetMapping("/detail")
	public ResultVO detail(@RequestParam("sessionId") String sessionId,@RequestParam("orderId") String orderId){
		if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(orderId)){
			throw new SellException(ResultEnum.QUERY_PARM_NOT_FULL);
		}
		String buyerOpenid = wechatSessionService.getOpenid(sessionId);
		BuyerOrderDetailVO oneOrder = orderService.findOneOrder(buyerOpenid, orderId);
		return ResultVOUtil.success(oneOrder);
	}
	//支付时返回订单总金额
	@GetMapping("/amount")
	public ResultVO amount(String sessionId,String orderId){
		String openid = wechatSessionService.getOpenid(sessionId);
		BigDecimal am =orderService.getAmount(openid, orderId);
		HashMap<String, String> map = new HashMap<>();
		map.put("amount", am.toString());
		return ResultVOUtil.success(map);
	}
	/** 返回订单列表*/
	@GetMapping("/list")
	public ResultVO list(String sessionId,@RequestParam(name = "page", defaultValue = "1")Integer page){
		String buyerOpenid = wechatSessionService.getOpenid(sessionId);
		if (StringUtils.isEmpty(buyerOpenid)){
			throw new SellException(ResultEnum.QUERY_PARM_NOT_FULL);
		}
		Map<String, Object> map = orderService.findList(buyerOpenid, page);
		return ResultVOUtil.success(map);
	}

	@GetMapping("/cancel")
	public ResultVO cancel(@RequestParam("orderId") String orderId, @RequestParam("sessionId") String sessionId){
		String buyerOpenid = wechatSessionService.getOpenid(sessionId);
		orderService.cancel(orderId, buyerOpenid);
		return ResultVOUtil.success();
	}
	@GetMapping("/paySuccess")
	public ResultVO paySuccess(String orderId){
		Map<String, Object> map = orderService.paySuccess(orderId);
		map.put("orderId", orderId);
		String s = com.alibaba.fastjson.JSON.toJSONString(map);
		orderWebSocket.sendMessage(s);
		return ResultVOUtil.success_Enum(ResultEnum.PAY_SUCCESS);
	}

	@PostMapping("/payFail")
	public ResultVO payFail(){
		return ResultVOUtil.success();
	}
}
