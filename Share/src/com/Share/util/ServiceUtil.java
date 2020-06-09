package com.Share.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.Share.entity.User;
import com.Share.service.IUserService;


public class ServiceUtil {

	private static IUserService<User> userService;
	
	public static IUserService<User> getUserService() {
		return userService;
	}
	
	public void setUserService(IUserService<User> userService) {
		ServiceUtil.userService = userService;
	}
    
    
}
