package com.xia.sell.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket")
public class OrderWebSocket {

	private Session session;
	private static CopyOnWriteArraySet<OrderWebSocket> webSocketSet = new CopyOnWriteArraySet<>();
	@OnOpen
	public void onOpen(Session session){
		this.session=session;
		webSocketSet.add(this);
		System.out.println("连接建立,连接总数："+webSocketSet.size());
	}
	@OnClose
	public void onClose(){
		webSocketSet.remove(this);
		System.out.println("连接关闭,连接总数："+webSocketSet.size());
	}
	@OnMessage
	public void message(String message){
		System.out.println("收到消息："+message);
	}

	public void sendMessage(String message){
		for (OrderWebSocket orderWebSocket : webSocketSet) {
			System.out.println("发送消息："+message);
			try {
				orderWebSocket.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
