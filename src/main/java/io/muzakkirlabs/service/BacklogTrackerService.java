package io.muzakkirlabs.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.muzakkirlabs.dto.InventoryMovementDTO;
import io.muzakkirlabs.model.Users;

@Service
public interface BacklogTrackerService {
	
	public void sendForgotPasswordEmail(String receiver, String token);
	
	public void sendEmail(String receiver, String subject, String body);
	
	public String getLoggedInUserName();
	
	public Users getLoggedInUser();
	
	public Map<String, String> getdashboardDetails();

	public List<InventoryMovementDTO> getInventoryMovement();

	public byte[] generateInvoice(String id);

}