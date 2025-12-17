package io.muzakkirlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
	
	Roles findByName(String name);

}