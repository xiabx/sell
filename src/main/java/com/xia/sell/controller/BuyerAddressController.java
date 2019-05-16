package com.xia.sell.controller;

import com.xia.sell.dto.BuyerAddressDTO;
import com.xia.sell.service.BuyerAddressService;
import com.xia.sell.service.WechatSessionService;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.vo.BuyerAddressVO;
import com.xia.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer/address")
public class BuyerAddressController {
	@Autowired
	private BuyerAddressService buyerAddressService;
	@Autowired
	private WechatSessionService wechatSessionService;
	@GetMapping("/list")
	public ResultVO list(String sessionId){
		String openid = wechatSessionService.getOpenid(sessionId);
		List<BuyerAddressVO> list = buyerAddressService.list(openid);
		ResultVO success = ResultVOUtil.success(list);
		return success;
	}

	@PostMapping("/save")
	public ResultVO save(@RequestBody BuyerAddressDTO addressDTO){
		if (!addressDTO.getAddressId().equals("")){
			//这里设置openid
			try {
				buyerAddressService.save(addressDTO);
				return ResultVOUtil.success(null);
			}catch (Exception e){
				e.printStackTrace();
				return ResultVOUtil.error(1, "失败，未知错误");
			}
		}else {
			buyerAddressService.saveNew(addressDTO);
			return ResultVOUtil.success(null);
		}
	}
	@GetMapping("/remove")
	public ResultVO remove(String addressId){
		try {
			buyerAddressService.remove(addressId);
			return ResultVOUtil.success(null);
		}catch (Exception e){
			e.printStackTrace();
			return ResultVOUtil.error(1, "失败，未知错误");
		}
	}
}
