package com.xia.sell.config;

import com.baidubce.services.bos.BosClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URL;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BOStest {
	@Autowired
	private BosClient client;
	@Test
	public void test(){
		//System.out.println(bosClient);
		// 获取指定文件
		File file = new File("C:\\Users\\xia\\Desktop\\wallhaven-668343.jpg");
		// 获取数据流
		//InputStream inputStream = new FileInputStream("/path/to/test.zip");

		// 以文件形式上传Object
		//PutObjectResponse putObjectFromFileResponse = client.putObject("waimai1996", "hi.jpg", file);
		// 以数据流形式上传Object
		//PutObjectResponse putObjectResponseFromInputStream = client.putObject(bucketName, objectKey, inputStream);
		//// 以二进制串上传Object
		//PutObjectResponse putObjectResponseFromByte = client.putObject(bucketName, objectKey, byte1);
		//// 以字符串上传Object
		//PutObjectResponse putObjectResponseFromString = client.putObject(bucketName, objectKey, string1);

		// 打印ETag
		//System.out.println(putObjectFromFileResponse.getETag());

		URL url = client.generatePresignedUrl("waimai1996","1.jpg", -1);
		//指定用户需要获取的Object所在的Bucket名称、该Object名称、时间戳、URL的有效时长

		System.out.println(url.toString());
	}
}
