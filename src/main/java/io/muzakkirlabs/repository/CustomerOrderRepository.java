package io.muzakkirlabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.muzakkirlabs.model.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	
	@Query("""
		    select co
		    from CustomerOrder co
		    join co.orderStatus os
		    where os.createdDate = (
		        select max(os2.createdDate)
		        from CustomerOrderStatus os2
		        where os2.customerOrder = co
		    )
		    and os.status = 'CREATED'
		""")
		List<CustomerOrder> getOrdersPendingApproval();

}