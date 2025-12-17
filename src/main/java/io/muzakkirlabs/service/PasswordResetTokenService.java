package io.muzakkirlabs.service;

import org.springframework.stereotype.Service;

@Service
public interface PasswordResetTokenService {
	
	String createResetPasswordEmail(String email);
	
	String updatePassword(String token, String password);

}
