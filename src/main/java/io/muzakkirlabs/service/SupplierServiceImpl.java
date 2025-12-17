package io.muzakkirlabs.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.muzakkirlabs.dto.OrderItemDTO;
import io.muzakkirlabs.dto.OrderItemDetialsDTO;
import io.muzakkirlabs.dto.PurchaseOrderDTO;
import io.muzakkirlabs.dto.PurchaseOrderFormDTO;
import io.muzakkirlabs.model.Inventory;
import io.muzakkirlabs.model.InventoryMovement;
import io.muzakkirlabs.model.Items;
import io.muzakkirlabs.model.PurchaseOrder;
import io.muzakkirlabs.model.PurchaseOrderItem;
import io.muzakkirlabs.model.PurchaseOrderStatus;
import io.muzakkirlabs.model.Suppliers;
import io.muzakkirlabs.model.Users;
import io.muzakkirlabs.repository.InventoryMovementRepository;
import io.muzakkirlabs.repository.InventoryRepository;
import io.muzakkirlabs.repository.ItemsRepository;
import io.muzakkirlabs.repository.PurchaseOrderRepository;
import io.muzakkirlabs.repository.SuppliersRepository;
import jakarta.transaction.Transactional;

@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	private SuppliersRepository suppliersRepository;
	
	@Autowired 
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	private BacklogTrackerService backlogTrackerService;
	
	@Autowired 
	private ItemsRepository itemsRepository;
	
	@Autowired
	private InventoryMovementRepository inventoryMovementRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public List<Suppliers> findAllActive() {
		return suppliersRepository.findByActiveTrue();
	}

	@Override
	public Suppliers add(String name, String contact, String phone, String email) {
		Suppliers supplier = new Suppliers(name, contact, phone, email, true);
		return suppliersRepository.save(supplier);
	}

	@Override
	public Suppliers changeState(String id, boolean state)  {
		Suppliers supplier = suppliersRepository.findById(Long.valueOf(id)).get();
		supplier.setActive(state);
		return suppliersRepository.save(supplier);
	}

	@Override
	public List<Suppliers> findAllInActive() {
		return suppliersRepository.findByActiveFalse();
	}

	@Override
	public Suppliers edit(String id, String name, String contact, String phone, String email) {
		Suppliers supplier = suppliersRepository.findById(Long.valueOf(id)).get();
		supplier.setName(name);
		supplier.setContact(contact);
		supplier.setPhone(phone);
		supplier.setEmail(email);
		return suppliersRepository.save(supplier);
	}

	@Transactional
	@Override
	public List<PurchaseOrderDTO> listOrders() {
		List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
		List<PurchaseOrderDTO> purchaseOrderDTOList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for(PurchaseOrder purchaseOrder : purchaseOrderList) {
			//Collections.sort(purchaseOrder.getPoItems());
			List<PurchaseOrderStatus> orderStatusList = purchaseOrder.getPoStatus();
			orderStatusList.sort(Comparator.naturalOrder());
			
			PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO(purchaseOrder.getId(), 
					purchaseOrder.getSupplier().getName(), purchaseOrder.getPoDate().format(formatter), 
					purchaseOrder.getTotalAmount(), orderStatusList.get(0).getStatus(), purchaseOrder.getPoItems().size());
			purchaseOrderDTOList.add(purchaseOrderDTO);
		}
		return purchaseOrderDTOList;
	}

	@Override
	public PurchaseOrder placeOrder(PurchaseOrderFormDTO purchaseOrderForm) {
		Users user = backlogTrackerService.getLoggedInUser();
		LocalDateTime currDate = LocalDateTime.now();
		BigDecimal orderTotal = BigDecimal.ZERO;
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
	    LocalDate poDate = LocalDate.parse(purchaseOrderForm.getPoDate(), dateFormatter);
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setSupplier(suppliersRepository.findById(Long.valueOf(purchaseOrderForm.getSupplier())).get());
		purchaseOrder.setPoDate(LocalDateTime.of(poDate, LocalDateTime.now().toLocalTime()));
		purchaseOrder.setCreatedBy(user);
		purchaseOrder.setCreatedDate(currDate);
		purchaseOrder.setUpdatedBy(user);
		purchaseOrder.setUpdatedDate(currDate);
		
		PurchaseOrderStatus orderStatus = new PurchaseOrderStatus(purchaseOrder, "CREATED", 
				user, currDate, user, currDate);
		purchaseOrder.getPoStatus().add(orderStatus);
		
		for(OrderItemDetialsDTO itemDetial : purchaseOrderForm.getItemDetails()) {
			Items items = itemsRepository.findById(Long.valueOf(itemDetial.getItemId())).get();
			PurchaseOrderItem orderItem = new PurchaseOrderItem(purchaseOrder, items, items.getName(), 
					new BigDecimal(itemDetial.getUnitPrice()), Integer.valueOf(itemDetial.getQuantity()));
			purchaseOrder.getPoItems().add(orderItem);
			orderTotal = orderTotal.add(orderItem.getUnitPrice()
					.multiply(BigDecimal.valueOf(orderItem.getQuantity())));
		}
		
		purchaseOrder.setTotalAmount(orderTotal);
		purchaseOrderRepository.save(purchaseOrder);
		return purchaseOrder;
	}

	@Transactional
	@Override
	public PurchaseOrder changeOrderStatus(String id, String status) {
		PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(Long.valueOf(id)).get();
		Users user = backlogTrackerService.getLoggedInUser();
		LocalDateTime currDate = LocalDateTime.now();
		PurchaseOrderStatus orderStatus = new PurchaseOrderStatus(purchaseOrder, status, 
				user, currDate, user, currDate);
		purchaseOrder.getPoStatus().add(orderStatus);
		purchaseOrderRepository.save(purchaseOrder);
		if("APPROVED".equalsIgnoreCase(status)) {
			for (PurchaseOrderItem orderItem : purchaseOrder.getPoItems()) {
				InventoryMovement inventoryMovement = new InventoryMovement();
				inventoryMovement.setItem(orderItem.getItem());
				inventoryMovement.setQuantity(orderItem.getQuantity());
				inventoryMovement.setMovementType("IN");
				inventoryMovement.setReferenceType("PURCHASE_ORDER_"+id);
				inventoryMovement.setCreatedBy(user);
				inventoryMovement.setCreatedDate(currDate);
				inventoryMovement.setUpdatedBy(user);
				inventoryMovement.setUpdatedDate(currDate);
				inventoryMovementRepository.save(inventoryMovement);

				Inventory inventory = inventoryRepository.findByItem(orderItem.getItem())
						.orElseGet(() -> {
							Inventory newInventory = new Inventory();
							newInventory.setItem(orderItem.getItem());
							newInventory.setQuantity(0);
							return newInventory;
						});

				inventory.setQuantity(inventory.getQuantity() + orderItem.getQuantity());
				inventory.setUpdatedAt(currDate);
				inventoryRepository.save(inventory);
			}
		}
		return purchaseOrder;
	}

	@Transactional
	@Override
	public List<OrderItemDTO> listOrderItems(String id) {
		PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(Long.valueOf(id)).get();
		List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
		for(PurchaseOrderItem orderItem : purchaseOrder.getPoItems()) {
			OrderItemDTO orderItemDTO = new OrderItemDTO();
			orderItemDTO.setItemName(orderItem.getItemName());
			orderItemDTO.setUnitPrice(orderItem.getUnitPrice());
			orderItemDTO.setQuantity(orderItem.getQuantity());
			orderItemDTO.setTotalPrice(orderItem.getUnitPrice()
					.multiply(BigDecimal.valueOf(orderItem.getQuantity())));
			orderItemDTOList.add(orderItemDTO);
		}
		return orderItemDTOList;
	}

	@Transactional
	@Override
	public List<PurchaseOrderDTO> getOrdersPendingApproval() {
		List<PurchaseOrder> pendingOrderList = purchaseOrderRepository.getOrdersPendingApproval();
		List<PurchaseOrderDTO> purchaseOrderDTOList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for(PurchaseOrder purchaseOrder : pendingOrderList) {
			List<PurchaseOrderStatus> orderStatusList = purchaseOrder.getPoStatus();
			orderStatusList.sort(Comparator.naturalOrder());
			
			PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO(purchaseOrder.getId(), 
					purchaseOrder.getSupplier().getName(), purchaseOrder.getPoDate().format(formatter), 
					purchaseOrder.getTotalAmount(), orderStatusList.get(0).getStatus(), purchaseOrder.getPoItems().size());
			purchaseOrderDTOList.add(purchaseOrderDTO);
		}
		return purchaseOrderDTOList;
	}

}