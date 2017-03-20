package com.voltor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.voltor.bean.User;

@Component
public class SecurityService {

	private static User CURRENT_USER;

	@Autowired
	private UserService userService; 

	public static User getCurrentUser() {
		return CURRENT_USER;
	}

	public boolean checkUserAndLogin(User user) {
		if( user.getAuthName() == null || user.getAuthPassword() == null ){
			return false;
		}
		User tryUser = userService.getUserByAuthNameForLogin(user.getAuthName());
		if( tryUser == null ){
			return false;
		}
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		if (passwordEncoder.matches(user.getAuthPassword(), tryUser.getAuthPassword())) {
			CURRENT_USER = tryUser; 
			return true;
		} 
		return false;
	}

}
