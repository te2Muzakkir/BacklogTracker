package io.muzakkirlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.CustomerOrderStatus;

@Repository
public interface CustomerOrderStatusRepository extends JpaRepository<CustomerOrderStatus, Long> {

}