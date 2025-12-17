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
@Table(name = "purchase_order")
public class PurchaseOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id")
	private Suppliers supplier;
	
	private LocalDateTime poDate;
	
	private BigDecimal totalAmount;
	
	@OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseOrderStatus> poStatus = new ArrayList<>();
	
	@OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseOrderItem> poItems = new ArrayList<>();
	
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

	public Suppliers getSupplier() {
		return supplier;
	}

	public void setSupplier(Suppliers supplier) {
		this.supplier = supplier;
	}

	public LocalDateTime getPoDate() {
		return poDate;
	}

	public void setPoDate(LocalDateTime poDate) {
		this.poDate = poDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<PurchaseOrderStatus> getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(List<PurchaseOrderStatus> poStatus) {
		this.poStatus = poStatus;
	}

	public List<PurchaseOrderItem> getPoItems() {
		return poItems;
	}

	public void setPoItems(List<PurchaseOrderItem> poItems) {
		this.poItems = poItems;
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