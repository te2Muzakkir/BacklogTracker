package io.muzakkirlabs.dto;

import java.util.List;

public class PurchaseOrderFormDTO {
	
	private String supplier;
    private String poDate;
    private List<OrderItemDetialsDTO> itemDetails;
    
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public List<OrderItemDetialsDTO> getItemDetails() {
		return itemDetails;
	}
	public void setItemDetails(List<OrderItemDetialsDTO> itemDetails) {
		this.itemDetails = itemDetails;
	}

}