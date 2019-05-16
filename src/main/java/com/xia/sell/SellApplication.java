package com.xia.sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xia.sell.dao")
public class SellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellApplication.class, args);
	}
	//@Value("${http.port}")
	//private Integer port;
	//
	//@Bean
	//public ServletWebServerFactory servletContainer() {
	//TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
	//tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
	//return tomcat;
	//}
	//
	//private Connector createStandardConnector() {
	//	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	//	connector.setPort(port);
	//	return connector;
	//}

}
