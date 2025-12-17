package io.muzakkirlabs.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_order_status")
public class CustomerOrderStatus  implements Comparable<CustomerOrderStatus> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private CustomerOrder customerOrder;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private Users createdBy;
	
	private LocalDateTime createdDate;
	
	@ManyToOne
	@JoinColumn(name = "updated_by")
	private Users updatedBy;
	
	private LocalDateTime updatedDate;
	
	public CustomerOrderStatus() {
		super();
	}
	
	public CustomerOrderStatus(CustomerOrder customerOrder, String status, Users createdBy, LocalDateTime createdDate,
			Users updatedBy, LocalDateTime updatedDate) {
		super();
		this.customerOrder = customerOrder;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public int compareTo(CustomerOrderStatus o) {
		return o.getCreatedDate().compareTo(this.getCreatedDate());
	}
	
}