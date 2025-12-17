package io.muzakkirlabs.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_order")
public class CustomerOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	private LocalDateTime orderDate;
	
	private BigDecimal totalAmount;
	
	@OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomerOrderStatus> orderStatus = new ArrayList<>();
	
	@OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomerOrderItem> orderItems = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "created_by")	
	private Users createdBy;
	
	private LocalDateTime createdDate;
	
	@ManyToOne
	@JoinColumn(name = "updated_by")
	private Users updatedBy;
	
	private LocalDateTime updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public List<CustomerOrderStatus> getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(List<CustomerOrderStatus> orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<CustomerOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<CustomerOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Users getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Users getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Users updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}