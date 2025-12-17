package io.muzakkirlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.PurchaseOrderStatus;

@Repository
public interface PurchaseOrderStatusRepository extends JpaRepository<PurchaseOrderStatus, Long> {

}