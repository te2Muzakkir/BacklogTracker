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

import io.muzakkirlabs.dto.OrderItemDTO;
import io.muzakkirlabs.dto.PurchaseOrderDTO;
import io.muzakkirlabs.dto.PurchaseOrderFormDTO;
import io.muzakkirlabs.model.Suppliers;
import io.muzakkirlabs.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	@GetMapping("/findAllActive")
	public List<Suppliers> findAllActive() {
		return supplierService.findAllActive();
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestParam("name") String name, @RequestParam("contact") String contact, 
			@RequestParam("phone") String phone, @RequestParam("email") String email) {
		supplierService.add(name, contact, phone, email);
		return ResponseEntity.ok().body("Supplier added successfully.");
	}
	
	@PostMapping("/edit")
	public ResponseEntity<String> edit(@RequestParam("id") String id, @RequestParam("name") String name, 
			@RequestParam("contact") String contact, @RequestParam("phone") String phone, @RequestParam("email") String email) {
		supplierService.edit(id, name, contact, phone, email);
		return ResponseEntity.ok().body("Supplier deleted successfully.");
	}
	
	@PostMapping("/changeState")
	public ResponseEntity<String> changeState(@RequestParam("id") String id, @RequestParam("state") boolean state) {
		supplierService.changeState(id, state);
		return ResponseEntity.ok().body(state? "Supplier successfully : Archived" : "Supplier successfully : Un Archived");
	}
	
	@GetMapping("/findAllInActive")
	public List<Suppliers> findAllInActive() {
		return supplierService.findAllInActive();
	}
	
	@GetMapping("/listOrders")
	public List<PurchaseOrderDTO> listOrders() {
		return supplierService.listOrders();
	}
	
	@GetMapping("/listOrderItems")
	public List<OrderItemDTO> listOrderItems(@RequestParam("id") String id) {
		return supplierService.listOrderItems(id);
	}
	
	@PostMapping("/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody PurchaseOrderFormDTO purchaseOrderForm) {
		supplierService.placeOrder(purchaseOrderForm);
		return ResponseEntity.ok().body("Order placed successfully.");
	}
	
	@GetMapping("/getOrdersPendingApproval")
	public List<PurchaseOrderDTO> getOrdersPendingApproval() {
		return supplierService.getOrdersPendingApproval();
	}
	
	@PostMapping("/changeOrderStatus")
	public ResponseEntity<String> changeOrderStatus(@RequestParam("id") String id, @RequestParam("status") String status) {
		supplierService.changeOrderStatus(id, status);
		return ResponseEntity.ok().body("Order status "+ status+" successfully.");
	}

}