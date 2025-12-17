package io.muzakkirlabs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.Inventory;
import io.muzakkirlabs.model.Items;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
	Optional<Inventory> findByItem(Items item);
	
	@Query("select sum(quantity) from Inventory")
	int totalItemsCount();

}