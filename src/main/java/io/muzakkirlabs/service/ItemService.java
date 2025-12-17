package io.muzakkirlabs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.muzakkirlabs.model.Items;

@Service
public interface ItemService {
	
	List<Items> findAll();

	Items add(String itemName, String sku, String category, String unitPrice);

	Items changeState(String id, boolean state);

	List<Items> findAllInActive();

	Items edit(String id, String itemName, String sku, String category, String unitPrice);
                
}