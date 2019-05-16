package com.xia.sell.service.impl;

import com.xia.sell.dao.WechatSessionMapper;
import com.xia.sell.dto.OpenidSessionKeyDTO;
import com.xia.sell.po.WechatSession;
import com.xia.sell.service.WechatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WechatSessionServiceImpl implements WechatSessionService {
	@Autowired
	private WechatSessionMapper wechatSessionMapper;

	@Override
	public String saveSession(OpenidSessionKeyDTO openidSessionKeyDTO) {
		WechatSession session = new WechatSession();
		String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
		session.setSessionId(sessionId);
		session.setOpenid(openidSessionKeyDTO.getOpenid());
		session.setSessionKey(openidSessionKeyDTO.getSession_key());
		wechatSessionMapper.insert(session);
		return sessionId;
	}

	public String getOpenid(String sessionId){
		WechatSession wechatSession = wechatSessionMapper.selectByPrimaryKey(sessionId);
		return wechatSession.getOpenid();
	}

	@Override
	public String getSession(String SessionId) {
		WechatSession session = wechatSessionMapper.selectByPrimaryKey(SessionId);
		if (session == null){
			return null;
		}
		return session.getSessionId();
	}
}
