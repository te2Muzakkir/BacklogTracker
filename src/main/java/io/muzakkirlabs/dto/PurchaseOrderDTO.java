package io.muzakkirlabs.dto;

import java.math.BigDecimal;

public class PurchaseOrderDTO {
	
	private Long id;
	
	private String supplierName;
	
	private String poDate;
	
	private BigDecimal totalAmount;
	
	private String status;
	
	private Integer totalItems;
	
	public PurchaseOrderDTO() {
		super();
	}
	
	public PurchaseOrderDTO(Long id, String supplierName, String poDate, BigDecimal totalAmount, String status,
			Integer totalItems) {
		super();
		this.id = id;
		this.supplierName = supplierName;
		this.poDate = poDate;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
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