package io.muzakkirlabs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.muzakkirlabs.dto.CustomerOrderDTO;
import io.muzakkirlabs.dto.CustomerOrderFormDTO;
import io.muzakkirlabs.dto.OrderItemDTO;
import io.muzakkirlabs.model.Customer;
import io.muzakkirlabs.model.CustomerOrder;

@Service
public interface CustomerService {

	List<Customer> findAllActive();

	Customer add(String name, String email, String phone, String address);

	Customer edit(String id, String name, String email, String phone, String address);

	Customer changeState(String id, boolean state);

	List<Customer> findAllInActive();

	List<CustomerOrderDTO> listOrders();

	CustomerOrder placeOrder(CustomerOrderFormDTO customerOrderForm);

	CustomerOrder changeOrderStatus(String id, String status);

	List<OrderItemDTO> listOrderItems(String id);

	List<CustomerOrderDTO> getOrdersPendingApproval();
	
}