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

import io.muzakkirlabs.dto.CustomerOrderDTO;
import io.muzakkirlabs.dto.CustomerOrderFormDTO;
import io.muzakkirlabs.dto.OrderItemDTO;
import io.muzakkirlabs.dto.OrderItemDetialsDTO;
import io.muzakkirlabs.model.Customer;
import io.muzakkirlabs.model.CustomerOrder;
import io.muzakkirlabs.model.CustomerOrderItem;
import io.muzakkirlabs.model.CustomerOrderStatus;
import io.muzakkirlabs.model.Inventory;
import io.muzakkirlabs.model.InventoryMovement;
import io.muzakkirlabs.model.Items;
import io.muzakkirlabs.model.Users;
import io.muzakkirlabs.repository.CustomerOrderRepository;
import io.muzakkirlabs.repository.CustomerRepository;
import io.muzakkirlabs.repository.InventoryMovementRepository;
import io.muzakkirlabs.repository.InventoryRepository;
import io.muzakkirlabs.repository.ItemsRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	private BacklogTrackerService backlogTrackerService;
	
	@Autowired 
	private ItemsRepository itemsRepository;
	
	@Autowired
	private InventoryMovementRepository inventoryMovementRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;

	@Override
	public List<Customer> findAllActive() {
		return customerRepository.findByActiveTrue();
	}

	@Override
	public Customer add(String name, String email, String phone, String address) {
		Users user = backlogTrackerService.getLoggedInUser();
		Customer customer = new Customer(name, email, phone, address, true, user, LocalDateTime.now(), user, LocalDateTime.now());
		return customerRepository.save(customer);
	}

	@Override
	public Customer edit(String id, String name, String email, String phone, String address) {
		Customer customer = customerRepository.findById(Long.valueOf(id)).get();
		customer.setAddress(address);
		customer.setEmail(email);
		customer.setName(name);
		customer.setPhone(phone);
		customer.setUpdatedBy(backlogTrackerService.getLoggedInUser());
		customer.setUpdatedDate(LocalDateTime.now());
		return customerRepository.save(customer); 
	}

	@Override
	public Customer changeState(String id, boolean state) {
		Customer customer = customerRepository.findById(Long.valueOf(id)).get();
		customer.setActive(state);
		customer.setUpdatedBy(backlogTrackerService.getLoggedInUser());
		customer.setUpdatedDate(LocalDateTime.now());
		return customerRepository.save(customer); 
	}

	@Override
	public List<Customer> findAllInActive() {
		return customerRepository.findByActiveFalse();
	}

	@Transactional
	@Override
	public List<CustomerOrderDTO> listOrders() {
		List<CustomerOrder> customerOrderList = customerOrderRepository.findAll();
		List<CustomerOrderDTO> customerOrderDTOList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for(CustomerOrder customerOrder : customerOrderList) {
			//Collections.sort(purchaseOrder.getPoItems());
			List<CustomerOrderStatus> orderStatusList = customerOrder.getOrderStatus();
			orderStatusList.sort(Comparator.naturalOrder());
			
			CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(customerOrder.getId(), 
					customerOrder.getCustomer().getName(), customerOrder.getOrderDate().format(formatter), 
					customerOrder.getTotalAmount(), orderStatusList.get(0).getStatus(), customerOrder.getOrderItems().size());
			customerOrderDTOList.add(customerOrderDTO);
		}
		return customerOrderDTOList;
	}

	@Override
	public CustomerOrder placeOrder(CustomerOrderFormDTO customerOrderForm) {
		Users user = backlogTrackerService.getLoggedInUser();
		LocalDateTime currDate = LocalDateTime.now();
		BigDecimal orderTotal = BigDecimal.ZERO;
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
		LocalDate poDate = LocalDate.parse(customerOrderForm.getCoDate(), dateFormatter);
		
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setCustomer(customerRepository.findById(Long.valueOf(customerOrderForm.getCustomer())).get());
		customerOrder.setOrderDate(LocalDateTime.of(poDate, LocalDateTime.now().toLocalTime()));
		customerOrder.setCreatedBy(user);
		customerOrder.setCreatedDate(currDate);
		customerOrder.setUpdatedBy(user);
		customerOrder.setUpdatedDate(currDate);
		
		CustomerOrderStatus customerOrderStatus = new CustomerOrderStatus(customerOrder, "CREATED", user, currDate, user, currDate);
		customerOrder.getOrderStatus().add(customerOrderStatus);
		
		for(OrderItemDetialsDTO itemDetial : customerOrderForm.getItemDetails()) {
			Items item = itemsRepository.findById(Long.valueOf(itemDetial.getItemId())).get();
			CustomerOrderItem orderItem = new CustomerOrderItem(customerOrder, item, item.getName(), 
					new BigDecimal(itemDetial.getUnitPrice()), Integer.valueOf(itemDetial.getQuantity()));
			customerOrder.getOrderItems().add(orderItem);
			orderTotal = orderTotal.add(orderItem.getUnitPrice()
					.multiply(BigDecimal.valueOf(orderItem.getQuantity())));
		}
		
		customerOrder.setTotalAmount(orderTotal);
		customerOrderRepository.save(customerOrder);
		return customerOrder;
	}

	@Transactional
	@Override
	public CustomerOrder changeOrderStatus(String id, String status) {
		Users user = backlogTrackerService.getLoggedInUser();
		LocalDateTime currDate = LocalDateTime.now();
		CustomerOrder customerOrder = customerOrderRepository.findById(Long.valueOf(id)).get();
		CustomerOrderStatus customerOrderStatus = new CustomerOrderStatus(customerOrder, status, user, currDate, user, currDate);
		customerOrder.getOrderStatus().add(customerOrderStatus);
		customerOrderRepository.save(customerOrder);
		if("APPROVED".equalsIgnoreCase(status)) {
			for (CustomerOrderItem orderItem : customerOrder.getOrderItems()) {
				InventoryMovement inventoryMovement = new InventoryMovement();
				inventoryMovement.setItem(orderItem.getItem());
				inventoryMovement.setQuantity(-orderItem.getQuantity());
				inventoryMovement.setMovementType("OUT");
				inventoryMovement.setReferenceType("CUSTOMER_ORDER_"+id);
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

				inventory.setQuantity(inventory.getQuantity() - orderItem.getQuantity());
				inventory.setUpdatedAt(currDate);
				inventoryRepository.save(inventory);
			}
		}
		return customerOrder;
	}

	@Transactional
	@Override
	public List<OrderItemDTO> listOrderItems(String id) {
		CustomerOrder customerOrder = customerOrderRepository.findById(Long.valueOf(id)).get();
		List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
		for(CustomerOrderItem orderItem : customerOrder.getOrderItems()) {
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
	public List<CustomerOrderDTO> getOrdersPendingApproval() {
		List<CustomerOrder> pendingOrderList = customerOrderRepository.getOrdersPendingApproval();
		List<CustomerOrderDTO> customerOrderDTOList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for(CustomerOrder customerOrder : pendingOrderList) {
			List<CustomerOrderStatus> orderStatusList = customerOrder.getOrderStatus();
			orderStatusList.sort(Comparator.naturalOrder());
			
			CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(customerOrder.getId(), 
					customerOrder.getCustomer().getName(), customerOrder.getOrderDate().format(formatter), 
					customerOrder.getTotalAmount(), orderStatusList.get(0).getStatus(), customerOrder.getOrderItems().size());
			customerOrderDTOList.add(customerOrderDTO);
		}
		return customerOrderDTOList;
	}

}