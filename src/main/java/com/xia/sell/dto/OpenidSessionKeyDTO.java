package com.xia.sell.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenidSessionKeyDTO {
	private String openid;
	private String session_key;
}
