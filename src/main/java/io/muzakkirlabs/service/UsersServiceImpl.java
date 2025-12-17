package io.muzakkirlabs.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.muzakkirlabs.model.Roles;
import io.muzakkirlabs.model.Users;
import io.muzakkirlabs.repository.RolesRepository;
import io.muzakkirlabs.repository.UsersRepository;
import jakarta.transaction.Transactional;

@Service
public class UsersServiceImpl implements UsersSerivce {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private RolesRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Users create(String username, String password, String email) {
		Users user = new Users();
	    user.setUsername(username);
	    user.setPassword(passwordEncoder.encode(password));
	    user.setEmail(email);
	    user.setCreatedOn(LocalDateTime.now());
	    Roles managerRole = roleRepository.findByName("MANAGER");
	    user.setRoles(Set.of(managerRole));
	    usersRepository.save(user);
		return user;
	}

	@Override
	@Transactional
	public void updatePassword(Users user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		usersRepository.save(user);
	}

}