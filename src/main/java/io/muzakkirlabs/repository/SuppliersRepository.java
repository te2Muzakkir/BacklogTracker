package io.muzakkirlabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.Suppliers;

@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Long> {
	
	List<Suppliers> findByActiveTrue();
	
	List<Suppliers> findByActiveFalse();

}