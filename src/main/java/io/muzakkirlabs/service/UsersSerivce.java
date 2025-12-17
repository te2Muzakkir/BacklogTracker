package io.muzakkirlabs.service;

import org.springframework.stereotype.Service;

import io.muzakkirlabs.model.Users;

@Service
public interface UsersSerivce {
	
	Users create(String username, String password, String email);
	
	void updatePassword(Users user, String password);

}