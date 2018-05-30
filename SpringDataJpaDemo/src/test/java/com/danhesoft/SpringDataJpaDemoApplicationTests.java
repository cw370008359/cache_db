package com.danhesoft;

import com.danhesoft.model.Users;
import com.danhesoft.util.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataJpaDemoApplicationTests {

	private static final String REMOTE_URL = "http://localhost:8080/user/";
	public static final String SAVE_URL = REMOTE_URL+"add";
	public static final String UPDATE_URL = REMOTE_URL+"update/";

	@Test
	public void contextLoads() {
	}

	/**
	 * 测试Post请求
     */
	@Test
	public void saveUser(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "小胖子");
		map.put("address", "淞虹公寓");
		map.put("phone", "13546568978");
		System.out.println(new HttpUtil().postData(SAVE_URL, map));
	}

	/**
	 * 测试Put请求
     */
	@Test
	public void updateUser(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "小胖子1");
		map.put("address", "淞虹公寓1");
		map.put("phone", "13546568978");
		System.out.println(new HttpUtil().putData(UPDATE_URL+"16", map));
	}

}
