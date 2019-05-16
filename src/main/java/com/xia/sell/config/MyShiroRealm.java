package com.xia.sell.config;

import com.xia.sell.po.SellerInfo;
import com.xia.sell.service.SellerInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	private SellerInfoService sellerInfoService;

	/**
	 * 登陆验证控制
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		String tokenUsername = token.getUsername();
		//判断用户名是否存在，若不存在则返回null，shiro则会抛出用户找不到的异常
		SellerInfo sellerInfo = sellerInfoService.selectSellerInfoByUsername(tokenUsername);
		if (sellerInfo ==null){
			return null;
		}
		ByteSource credentialsSalt = ByteSource.Util.bytes(tokenUsername);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(tokenUsername, sellerInfo.getPassword(),credentialsSalt, "");
		return authenticationInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
}
