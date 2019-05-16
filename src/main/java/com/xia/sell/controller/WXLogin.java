package com.xia.sell.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xia.sell.dto.OpenidSessionKeyDTO;
import com.xia.sell.service.WechatSessionService;
import com.xia.sell.util.ResultVOUtil;
import com.xia.sell.util.encryption.WXCore;
import com.xia.sell.vo.ResultVO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/buyer")
public class WXLogin {
	private final String AppID="wx58259b64e95c5edb";
	private final String AppSecret="6c684d49710dd4fa4468ab1d74f7a14f";
	@Autowired
	private WechatSessionService wechatSessionService;
	private String sessionKey;
	@GetMapping("/WXLogin")
	public String login(String code) throws IOException {
		OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url("https://api.weixin.qq.com/sns/jscode2session?appid="+AppID+"&secret="+AppSecret+"&js_code="+code+"&grant_type=authorization_code").build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				//session转换
				ObjectMapper mapper = new ObjectMapper();
				OpenidSessionKeyDTO openidSessionKey = mapper.readValue(response.body().string(), OpenidSessionKeyDTO.class);
				//sessionKey=openidSessionKey.getSession_key();
				String sessionId = wechatSessionService.saveSession(openidSessionKey);
				return sessionId;
			} else {
				throw new IOException("Unexpected code " + response);
		}
	}
	/** 校验rawData*/
	@GetMapping("/checkRawData")
	public String checkRawData(String rawData,String signature) throws UnsupportedEncodingException {
		//todo 转换成String
		String sha1 = new String(DigestUtils.sha(rawData+sessionKey));
		System.out.println("sha1:  "+sha1);
		return "";
	}
	@GetMapping("/decrypt")
	public String decrypt(String encryptedData,String iv){
		String decrypt = WXCore.decrypt(AppID, encryptedData, sessionKey, iv);
		return "";
	}
	@GetMapping("/checkSessionExist")
	//@ResponseBody
	public ResultVO checkSessionExist(String sessionId){
		if (sessionId == null){
			return ResultVOUtil.success("noExist");
		}
		String session = wechatSessionService.getSession(sessionId);
		if (session == null){
			return ResultVOUtil.success("noExist");
		}else{
			return ResultVOUtil.success(session);
		}
	}

}
