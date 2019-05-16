package com.xia.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 创建订单传输数据的接受对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO {
	@NotBlank(message = "地址不可以为空")
	private String addressId;
	@NotBlank(message = "买家信息不完整")
	private String sessionId;
	private String note;
	@NotBlank(message = "买家信息不完整")
	private String sellerId;
	@NotEmpty(message = "告诉我你是从哪来的")
	private List<OrderProductInfoDTO> items;

}
