package io.muzakkirlabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.Customer;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {
	
	List<Customer> findByActiveTrue();
	
	List<Customer> findByActiveFalse();

}