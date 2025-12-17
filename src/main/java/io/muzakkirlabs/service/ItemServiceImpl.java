package io.muzakkirlabs.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muzakkirlabs.model.Items;
import io.muzakkirlabs.repository.ItemsRepository;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemsRepository itemsRepository;

	@Override
	public List<Items> findAll() {
		return itemsRepository.findByActiveTrue();
	}

	@Override
	public Items add(String itemName, String sku, String category, String unitPrice) {
		Items item = new Items(sku, itemName, category, new BigDecimal(unitPrice), true, LocalDateTime.now());
		return itemsRepository.save(item);
	}

	@Override
	public Items changeState(String id, boolean state) {
		Items item = itemsRepository.findById(Long.parseLong(id)).get();
		item.setActive(state);
		return itemsRepository.save(item);
	}

	@Override
	public List<Items> findAllInActive() {
		return itemsRepository.findByActiveFalse();
	}

	@Override
	public Items edit(String id, String itemName, String sku, String category, String unitPrice) {
		Items item = itemsRepository.findById(Long.parseLong(id)).get();
		item.setName(itemName);
		item.setSku(sku);
		item.setCategory(category);
		item.setUnitPrice(new BigDecimal(unitPrice));
		return itemsRepository.save(item);
	}

}