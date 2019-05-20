package com.xia.sell.controller;

import com.xia.sell.exception.SellException;
import com.xia.sell.service.OrderService;
import com.xia.sell.service.SellerOrderService;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.vo.ResultVO;
import com.xia.sell.vo.SellerOrderVO;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {
	@Autowired
	private SellerOrderService sellerOrderService;
	@Autowired
	private OrderService orderService;

	/**
	 * 当前商家所有订单
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String orderList(@RequestParam(value = "page",defaultValue = "1") Integer page, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		String sellerId = (String) session.getAttribute("sellerId");

		HashMap<String, Object> allOrder = sellerOrderService.getAllOrder(page, sellerId);

		List<SellerOrderVO> list = (List<SellerOrderVO>) allOrder.get("list");
		model.addAttribute("sellerOrderVO", list);
		model.addAttribute("pageInfo", allOrder);
		return "index";
	}
	@GetMapping("/listPage")
	@ResponseBody
	public Object orderListForPage(@RequestParam(value = "page",defaultValue = "1") Integer page, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		String sellerId = (String) session.getAttribute("sellerId");

		HashMap<String, Object> allOrder = sellerOrderService.getAllOrder(page, sellerId);

		//List<SellerOrderVO> list = (List<SellerOrderVO>) allOrder.get("list");

		return allOrder;
	}

	@GetMapping("/cancel")
	@ResponseBody
	public ResultVO cancel(String orderId){
		try {
			sellerOrderService.cancelOrder(orderId);
			return ResultVOUtil.success();
		}catch (SellException e){
			e.printStackTrace();
			return ResultVOUtil.error(1, e.getMessage());
		}
	}
	@GetMapping("/setDistribution")
	@ResponseBody
	public ResultVO setDistribution(String orderId){
		try {
			sellerOrderService.setDistribution(orderId);
			return ResultVOUtil.success();
		}catch (SellException e){
			e.printStackTrace();
			return ResultVOUtil.error(1, e.getMessage());
		}
	}
	@GetMapping("/setDistributionFinish")
	@ResponseBody
	public ResultVO setDistributionFinish(String orderId){
		try {
			sellerOrderService.setDistributionFinish(orderId);
			return ResultVOUtil.success();
		}catch (SellException e){
			e.printStackTrace();
			return ResultVOUtil.error(1, e.getMessage());
		}
	}
	//卖家接单
	@GetMapping("/alreadyOrder")
	@ResponseBody
	public ResultVO finish(String orderId){
		try {
			sellerOrderService.alreadyOrder(orderId);
			return ResultVOUtil.success();
		}catch (SellException e){
			e.printStackTrace();
			return ResultVOUtil.error();
		}
	}
	@GetMapping("/details")
	@ResponseBody
	public Map<String, Object> details(String  orderId){
		Map<String, Object> one = sellerOrderService.findOne(orderId);
		//orderInfoVO.setOrderStatusStr();
		return one;
	}
}
