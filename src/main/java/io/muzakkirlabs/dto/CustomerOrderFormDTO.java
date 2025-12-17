package io.muzakkirlabs.dto;

import java.util.List;

public class CustomerOrderFormDTO {
	
	private String customer;
    private String coDate;
    private List<OrderItemDetialsDTO> itemDetails;
    
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCoDate() {
		return coDate;
	}
	public void setCoDate(String coDate) {
		this.coDate = coDate;
	}
	public List<OrderItemDetialsDTO> getItemDetails() {
		return itemDetails;
	}
	public void setItemDetails(List<OrderItemDetialsDTO> itemDetails) {
		this.itemDetails = itemDetails;
	}
    
}