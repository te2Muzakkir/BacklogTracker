package io.muzakkirlabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.Items;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
	
	
	List<Items> findByActiveFalse();
	
	List<Items> findByActiveTrue();

}