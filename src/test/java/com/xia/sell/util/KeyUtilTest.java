package com.xia.sell.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

public class KeyUtilTest {

	@Test
	public void getKey() {
		String hashAlgorithmName = "MD5";
		String credentials = "123";
		int hashIterations = 1024;
		ByteSource credentialsSalt = ByteSource.Util.bytes("123");
		Object obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
		System.out.println(obj);
	}
}