package io.muzakkirlabs.dto;

import java.math.BigDecimal;

public class CustomerOrderDTO {
	
private Long id;
	
	private String customerName;
	
	private String coDate;
	
	private BigDecimal totalAmount;
	
	private String status;
	
	private Integer totalItems;
	
	public CustomerOrderDTO() {
		super();
	}
	
	public CustomerOrderDTO(Long id, String customerName, String coDate, BigDecimal totalAmount, String status,
			Integer totalItems) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.coDate = coDate;
		this.totalAmount = totalAmount;
		this.status = status;
		this.totalItems = totalItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCoDate() {
		return coDate;
	}

	public void setCoDate(String coDate) {
		this.coDate = coDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}
	
}