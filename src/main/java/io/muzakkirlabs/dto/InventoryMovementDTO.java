package io.muzakkirlabs.dto;

public class InventoryMovementDTO {
	
	private String createdDate;
	private String itemName;
	private String quantity;
	private String movementType;
	private String refrence;
	private String createdBy;
	
	public InventoryMovementDTO() {
	}
	
	public InventoryMovementDTO(String createdDate, String itemName, String quantity, String movementType,
			String refrence, String createdBy) {
		super();
		this.createdDate = createdDate;
		this.itemName = itemName;
		this.quantity = quantity;
		this.movementType = movementType;
		this.refrence = refrence;
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getMovementType() {
		return movementType;
	}
	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}
	public String getRefrence() {
		return refrence;
	}
	public void setRefrence(String refrence) {
		this.refrence = refrence;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}