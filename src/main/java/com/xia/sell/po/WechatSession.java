package com.xia.sell.po;

public class WechatSession {
    private String sessionId;

    private String sessionKey;

    private String openid;

    public WechatSession(String sessionId, String sessionKey, String openid) {
        this.sessionId = sessionId;
        this.sessionKey = sessionKey;
        this.openid = openid;
    }

    public WechatSession() {
        super();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey == null ? null : sessionKey.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }
}