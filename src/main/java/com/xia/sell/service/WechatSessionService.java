package com.xia.sell.service;

import com.xia.sell.dto.OpenidSessionKeyDTO;

public interface WechatSessionService {
	String saveSession(OpenidSessionKeyDTO openidSessionKeyDTO);
	String getOpenid(String sessionId);
	String getSession(String SessionId);
}
