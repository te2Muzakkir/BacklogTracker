package io.muzakkirlabs.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muzakkirlabs.model.PasswordResetToken;
import io.muzakkirlabs.model.Users;
import io.muzakkirlabs.repository.PasswordResetTokenRepository;
import io.muzakkirlabs.repository.UsersRepository;
import jakarta.transaction.Transactional;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
    private BacklogTrackerService backlogTrackerService;
	
	@Autowired
	private UsersSerivce usersSerivce;

	@Override
	public String createResetPasswordEmail(String email) {
		Optional<Users> user = usersRepository.findByEmail(email);
		if(!user.isPresent())
			return "No user found with email : "+email;
		String token = generateRandomToken();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user.get());
        passwordResetToken.setToken(token);
        passwordResetToken.setCreatedAt(LocalDateTime.now());
        passwordResetToken.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        passwordResetTokenRepository.save(passwordResetToken);
        backlogTrackerService.sendForgotPasswordEmail(email, token);
        return "Reset password Email sent successfully.";
	}

    private String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[12]; // 12 bytes ≈ 16 chars when Base64URL
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 16);
    }

	@Override
	@Transactional
	public String updatePassword(String token, String password) {
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
		if(passwordResetToken == null)
			return "Error: Invalid token.";
		if(passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now()))
			return "Error: Expired token, Please generate a new one.";
		usersSerivce.updatePassword(passwordResetToken.getUser(), password);
		return "Password updated successfully";
	}

}