package io.muzakkirlabs.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

	@Query("""
			    select po
			    from PurchaseOrder po
			    join po.poStatus ps
			    where ps.createdDate = (
			        select max(ps2.createdDate)
			        from PurchaseOrderStatus ps2
			        where ps2.purchaseOrder = po
			    )
			    and ps.status = 'CREATED'
			""")
	List<PurchaseOrder> getOrdersPendingApproval();


	@Query("""
			select po from PurchaseOrder po
			where po.createdDate >= :startDate
			and po.createdDate < :endDate
			""")
	List<PurchaseOrder> getOrdersCreatedToday(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}