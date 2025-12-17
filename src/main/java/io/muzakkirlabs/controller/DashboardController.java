package io.muzakkirlabs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.muzakkirlabs.dto.InventoryMovementDTO;
import io.muzakkirlabs.service.BacklogTrackerService;

@RestController
public class DashboardController {
	
	@Autowired
	private BacklogTrackerService backlogTrackerService;
	
	@GetMapping("/dashboard/inventory-movement")
	public List<InventoryMovementDTO> getInventoryMovement() {
		return backlogTrackerService.getInventoryMovement();
	}
	
	@GetMapping(value = "/generateInvoice", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> generateInvoice(@RequestParam String id) {
	    byte[] pdf = backlogTrackerService.generateInvoice(id);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=backlog_invoice.pdf")
	        .body(pdf);
	}

}