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
@Table(name = "purchase_order_status")
public class PurchaseOrderStatus implements Comparable<PurchaseOrderStatus>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "po_id")
	private PurchaseOrder purchaseOrder;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private Users createdBy;
	
	private LocalDateTime createdDate;
	
	@ManyToOne
	@JoinColumn(name = "updated_by")
	private Users updatedBy;
	
	private LocalDateTime updatedDate;
	
	public PurchaseOrderStatus() {
		super();
	}
	
	public PurchaseOrderStatus(PurchaseOrder purchaseOrder, String status, Users createdBy, LocalDateTime createdDate,
			Users updatedBy, LocalDateTime updatedDate) {
		super();
		this.purchaseOrder = purchaseOrder;
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

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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
	public int compareTo(PurchaseOrderStatus o) {
		return o.getCreatedDate().compareTo(this.createdDate);
	}
	
}