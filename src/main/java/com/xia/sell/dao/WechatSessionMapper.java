package com.xia.sell.dao;

import com.xia.sell.po.WechatSession;

public interface WechatSessionMapper {
    int deleteByPrimaryKey(String sessionId);

    int insert(WechatSession record);

    int insertSelective(WechatSession record);

    WechatSession selectByPrimaryKey(String sessionId);

    int updateByPrimaryKeySelective(WechatSession record);

    int updateByPrimaryKey(WechatSession record);
}