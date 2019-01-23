package com.mkt.corevalue.authentication.service;



import com.mkt.corevalue.authentication.controller.LoginUser;

public interface UserService {

	public LoginUser save(LoginUser user) ;
	
	public String generateToken(LoginUser user) ;
		

}
