package io.muzakkirlabs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.muzakkirlabs.model.Items;
import io.muzakkirlabs.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/findAll")
	public List<Items> findAll() {
		return itemService.findAll();
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestParam("itemName") String itemName, @RequestParam("sku") String sku, 
			@RequestParam("category") String category, @RequestParam("unitPrice") String unitPrice) {
		itemService.add(itemName, sku, category, unitPrice);
		return ResponseEntity.ok().body("Item added successfully.");
	}
	
	@PostMapping("/edit")
	public ResponseEntity<String> edit(@RequestParam("id") String id, @RequestParam("itemName") String itemName, 
			@RequestParam("sku") String sku, @RequestParam("category") String category, @RequestParam("unitPrice") String unitPrice) {
		itemService.edit(id, itemName, sku, category, unitPrice);
		return ResponseEntity.ok().body("Item deleted successfully.");
	}
	
	@PostMapping("/changeState")
	public ResponseEntity<String> changeState(@RequestParam("id") String id, @RequestParam("state") boolean state) {
		itemService.changeState(id, state);
		return ResponseEntity.ok().body(state? "Item successfully : Archived" : "Item successfully : Un Archived");
	}
	
	@GetMapping("/findAllInActive")
	public List<Items> findAllInActive() {
		return itemService.findAllInActive();
	}

}