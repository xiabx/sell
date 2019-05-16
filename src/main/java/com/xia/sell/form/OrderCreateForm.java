package com.xia.sell.form;

import com.xia.sell.dto.OrderProductInfoDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderCreateForm {
	@NotEmpty(message = "收货人姓名不可以为空")
	private String buyerName;
	@NotEmpty(message = "收货人电话不可以为空")
	private String buyerPhone;
	@NotEmpty(message = "收货人地址不可以为空")
	private String buyerAddress;
	@NotEmpty(message = "收货人openid不可以为空")
	private String buyerOpenid;
	@NotEmpty(message = "购物车不可以为空")
	private List<OrderProductInfoDTO> items;
}

