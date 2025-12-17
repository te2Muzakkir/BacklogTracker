package io.muzakkirlabs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.muzakkirlabs.dto.PurchaseOrderDTO;
import io.muzakkirlabs.dto.PurchaseOrderFormDTO;
import io.muzakkirlabs.dto.OrderItemDTO;
import io.muzakkirlabs.model.PurchaseOrder;
import io.muzakkirlabs.model.Suppliers;

@Service
public interface SupplierService {
	
	List<Suppliers> findAllActive();
	
	Suppliers add(String name, String contact, String phone, String email);
	
	Suppliers changeState(String id, boolean state);

	List<Suppliers> findAllInActive();

	Suppliers edit(String id, String name, String contact, String phone, String email);

	List<PurchaseOrderDTO> listOrders();

	PurchaseOrder placeOrder(PurchaseOrderFormDTO purchaseOrderForm);

	PurchaseOrder changeOrderStatus(String id, String status);

	List<OrderItemDTO> listOrderItems(String id);

	List<PurchaseOrderDTO> getOrdersPendingApproval();

}