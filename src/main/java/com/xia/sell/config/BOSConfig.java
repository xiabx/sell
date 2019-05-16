package com.xia.sell.config;

import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BOSConfig {
	@Bean
	public BosClient bosClient(){
		String endpoint = "bj.bcebos.com";
		String ak = "0b8f90ee057342ce960257df33e0ec7b";
		String sk = "e931aadfe0af47f8a02332aa032e1e40";
		BosClientConfiguration config = new BosClientConfiguration();
		config.setCredentials(new DefaultBceCredentials(ak, sk));
		config.setEndpoint(endpoint);
		config.setProtocol(Protocol.HTTPS);
		BosClient client = new BosClient(config);
		return client;
	}
}
