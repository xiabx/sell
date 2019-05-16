package com.xia.sell.service;

import com.xia.sell.dto.BuyerAddressDTO;
import com.xia.sell.vo.BuyerAddressVO;

import java.util.List;

public interface BuyerAddressService {

	List<BuyerAddressVO> list(String openid);

	void save(BuyerAddressDTO addressDTO);

	void remove(String addressId);

	void saveNew(BuyerAddressDTO addressDTO);
}
