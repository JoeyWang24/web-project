package com.Share.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.Share.entity.User;
import com.Share.service.IUserService;
import com.Share.service.impl.UserService;

public class UserServiceTest {

	ApplicationContext ap=new ClassPathXmlApplicationContext("/spring-context.xml");
	
	@Test
	public void testSave() throws Exception {
		System.out.println(ap);
		IUserService<User> userService= (IUserService<User>) ap.getBean("userService");
		
		User user=new User();
		user.setUsername("eric");
		user.setPassword("1234");
		user.setGender("男");
		user.setStatus(0);
		user.setCreateTime(new Timestamp(System.currentTimeMillis()));
		user.setImgPath("upload/123.jpg");
		
		userService.saveUser(user);
	}

}
