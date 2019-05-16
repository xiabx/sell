package com.xia.sell.service.impl;

import com.xia.sell.dao.BuyerAddressMapper;
import com.xia.sell.dto.BuyerAddressDTO;
import com.xia.sell.po.BuyerAddress;
import com.xia.sell.service.BuyerAddressService;
import com.xia.sell.service.WechatSessionService;
import com.xia.sell.util.KeyUtil;
import com.xia.sell.vo.BuyerAddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BuyerAddressServiceImpl implements BuyerAddressService {
	@Autowired
	private BuyerAddressMapper buyerAddressMapper;
	@Autowired
	private WechatSessionService wechatSessionService;
	@Override
	public List<BuyerAddressVO> list(String openid) {
		List<BuyerAddress> buyerAddressList = buyerAddressMapper.selectByOpenid(openid);
		ArrayList<BuyerAddressVO> buyerAddressVOS = new ArrayList<>();
		for (BuyerAddress buyerAddress : buyerAddressList) {
			BuyerAddressVO buyerAddressVO = new BuyerAddressVO();
			BeanUtils.copyProperties(buyerAddress, buyerAddressVO);
			buyerAddressVOS.add(buyerAddressVO);
		}
		return buyerAddressVOS;
	}

	@Override
	public void save(BuyerAddressDTO addressDTO) {
		BuyerAddress buyerAddress = new BuyerAddress();
		BeanUtils.copyProperties(addressDTO, buyerAddress);
		buyerAddress.setOpenid(wechatSessionService.getOpenid(addressDTO.getSessionId()));
		buyerAddressMapper.updateByPrimaryKeySelective(buyerAddress);
	}

	@Override
	public void remove(String addressId) {
		buyerAddressMapper.deleteByPrimaryKey(addressId);
	}

	@Override
	public void saveNew(BuyerAddressDTO addressDTO) {
		String openid = wechatSessionService.getOpenid(addressDTO.getSessionId());
		BuyerAddress buyerAddress = new BuyerAddress();
		BeanUtils.copyProperties(addressDTO, buyerAddress);
		buyerAddress.setAddressId(KeyUtil.getBuyerAddressKey());
		buyerAddress.setOpenid(openid);
		buyerAddressMapper.insertSelective(buyerAddress);
	}
}
