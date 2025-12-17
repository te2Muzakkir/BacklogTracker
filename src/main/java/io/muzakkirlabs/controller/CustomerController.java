package io.muzakkirlabs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.muzakkirlabs.dto.CustomerOrderDTO;
import io.muzakkirlabs.dto.CustomerOrderFormDTO;
import io.muzakkirlabs.dto.OrderItemDTO;
import io.muzakkirlabs.model.Customer;
import io.muzakkirlabs.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/findAllActive")
	public List<Customer> findAllActive() {
		return customerService.findAllActive();
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestParam("name") String name, @RequestParam("email") String email, 
			@RequestParam("phone") String phone, @RequestParam("address") String address) {
		customerService.add(name, email, phone, address);
		return ResponseEntity.ok().body("Item added successfully.");
	}
	
	@PostMapping("/edit")
	public ResponseEntity<String> edit(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("email") String email, 
			@RequestParam("phone") String phone, @RequestParam("address") String address) {
		customerService.edit(id, name, email, phone, address);
		return ResponseEntity.ok().body("Item deleted successfully.");
	}
	
	@PostMapping("/changeState")
	public ResponseEntity<String> changeState(@RequestParam("id") String id, @RequestParam("state") boolean state) {
		customerService.changeState(id, state);
		return ResponseEntity.ok().body("Item archived successfully.");
	}
	
	@GetMapping("/findAllInActive")
	public List<Customer> findAllInActive() {
		return customerService.findAllInActive();
	}
	
	@GetMapping("/listOrders")
	public List<CustomerOrderDTO> listOrders() {
		return customerService.listOrders();
	}
	
	@GetMapping("/listOrderItems")
	public List<OrderItemDTO> listOrderItems(@RequestParam("id") String id) {
		return customerService.listOrderItems(id);
	}
	
	@PostMapping("/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody CustomerOrderFormDTO customerOrderForm) {
		customerService.placeOrder(customerOrderForm);
		return ResponseEntity.ok().body("Order placed successfully.");
	}
	
	@GetMapping("/getOrdersPendingApproval")
	public List<CustomerOrderDTO> getOrdersPendingApproval() {
		return customerService.getOrdersPendingApproval();
	}
	
	@PostMapping("/changeOrderStatus")
	public ResponseEntity<String> changeOrderStatus(@RequestParam("id") String id, @RequestParam("status") String status) {
		customerService.changeOrderStatus(id, status);
		return ResponseEntity.ok().body("Order status "+ status+" successfully.");
	}

}