package com.mkt.corevalue.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mkt.corevalue.authentication.controller.LoginUser;
import com.mkt.corevalue.authentication.util.JwtTokenUtilNew;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	
	@Autowired
	private JwtTokenUtilNew jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		User user = new User();
		
		return new org.springframework.security.core.userdetails.User(
				user.getName(), user.getPassword(), null);
	}

	@Override
	public LoginUser save(LoginUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateToken(LoginUser user) {
		// TODO Auto-generated method stub
		return jwtTokenUtil.generateToken(user.getUsername());
	}

}
