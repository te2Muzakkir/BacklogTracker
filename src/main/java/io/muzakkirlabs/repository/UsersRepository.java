package io.muzakkirlabs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	Optional<Users> findByEmail(String email);
	
	Users findByUsername(String username);

}