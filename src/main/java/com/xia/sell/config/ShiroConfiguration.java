package com.xia.sell.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
	//将自己的验证方式加入容器
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		//md5 加密
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("MD5");
		matcher.setHashIterations(1024);
		myShiroRealm.setCredentialsMatcher(matcher);
		return myShiroRealm;
	}

	//权限管理，配置主要是Realm的管理认证
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
		manager.setRealm(myShiroRealm());
		manager.setRememberMeManager(rememberMeManager());

		return manager;
	}

	//Filter工厂，设置对应的过滤条件和跳转条件
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//此处用LinkedHashMap，保证有序
		Map<String,String> map = new LinkedHashMap<>();
		//登出
		map.put("/seller/logout","logout");
		map.put("/seller/tologin","anon");
		map.put("/admin", "roles[admin]");
		map.put("/css/**", "anon");
		map.put("/favicon.ico", "anon");
		map.put("/js/**", "anon");
		map.put("/img/**", "anon");
		map.put("/fonts/**", "anon");
		map.put("/buyer/**", "anon");
		map.put("/WXLogin/**", "anon");
		map.put("/layui/**", "anon");
		//对所有用户认证
		map.put("/**","authc");
		//登录连接
		shiroFilterFactoryBean.setLoginUrl("/seller/tologin");
		//登陆成功后要跳转的连接
		shiroFilterFactoryBean.setSuccessUrl("/seller/order/list");
		//错误页面，授权不通过跳转
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

	//加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * cookie对象;
	 * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie(){
		//System.out.println("ShiroConfiguration.rememberMeCookie()");
		//这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		//<!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(259200);
		return simpleCookie;
	}

	/**
	 * cookie管理对象;
	 * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(){
		//System.out.println("ShiroConfiguration.rememberMeManager()");
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
		return cookieRememberMeManager;
	}

	/**
	 * 启用shiro的thymeleaf标签
	 * @return
	 */
	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect(){
		return new ShiroDialect();
	}

}