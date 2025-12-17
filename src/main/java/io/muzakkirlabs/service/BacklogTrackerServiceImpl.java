package io.muzakkirlabs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.muzakkirlabs.dto.InventoryMovementDTO;
import io.muzakkirlabs.model.Inventory;
import io.muzakkirlabs.model.InventoryMovement;
import io.muzakkirlabs.model.Users;
import io.muzakkirlabs.repository.CustomerOrderRepository;
import io.muzakkirlabs.repository.InventoryMovementRepository;
import io.muzakkirlabs.repository.InventoryRepository;
import io.muzakkirlabs.repository.PurchaseOrderRepository;
import io.muzakkirlabs.repository.UsersRepository;
import jakarta.annotation.Resource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class BacklogTrackerServiceImpl implements BacklogTrackerService {
	
	@Resource(mappedName = "java:jboss/mail/GmailSMTP")
	private Session mailSession;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	private InventoryMovementRepository inventoryMovementRepository;
	
	@Override
	public void sendForgotPasswordEmail(String receiver, String token) {
		//TODO : get link from db
		String resetLink = "http://localhost:8080/backlogtracker/updatePasswordScreen?token="+token;
	    String subject = "Backlog Tracker - Forgot Password";
	    StringBuilder body = new StringBuilder("Hello,\n\n")
	    		.append("We received a request to reset your password. Use the link below:\n\n" )
	    		.append(resetLink + "\n\n" )
	    		.append("This link is valid for 5 minutes. If it expires, request a new one.\n\n")
	    		.append("If you did not request this, ignore this email.\n\n")
	    		.append("Regards,\nBacklogTracker Security Team");
	    sendEmail(receiver, subject, body.toString());
	}

	@Override
	public void sendEmail(String receiver, String subject, String body) {
	    try {
	    	MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(System.getProperty("smtp.username")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
		    message.setSubject(subject);
		    message.setText(body);
		    Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getLoggedInUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	@Override
	public Users getLoggedInUser() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return usersRepository.findByUsername(name);
	}

	@Override
	public Map<String, String> getdashboardDetails() {
		Map<String, String> valueMap = new HashMap<>();
		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay(); 
		List<Inventory> inventoryList = inventoryRepository.findAll();
		int totalInventoryCount = 0;
		int lowInventoryCount = 0;
		for(Inventory inventory : inventoryList) {
			totalInventoryCount += inventory.getQuantity();
			if(inventory.getQuantity() < 20)
				lowInventoryCount++;
		}
		int pendingOrderCount = customerOrderRepository.getOrdersPendingApproval().size();
		int poCreatedTodayCount = purchaseOrderRepository.getOrdersCreatedToday(startOfDay, endOfDay).size();
		valueMap.put("totalInventoryCount", String.valueOf(totalInventoryCount));
		valueMap.put("lowInventoryCount", String.valueOf(lowInventoryCount));
		valueMap.put("pendingOrderCount", String.valueOf(pendingOrderCount));
		valueMap.put("poCreatedTodayCount", String.valueOf(poCreatedTodayCount));
		return valueMap;
	}

	@Transactional(readOnly = true)
	@Override
	public List<InventoryMovementDTO> getInventoryMovement() {
		List<InventoryMovementDTO> movementDTOList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		for(InventoryMovement inventoryMovement : inventoryMovementRepository.findAllByOrderByCreatedDateDesc()) {
			InventoryMovementDTO inventoryMovementDTO = new InventoryMovementDTO(inventoryMovement.getCreatedDate().format(formatter), 
					inventoryMovement.getItem().getName(), String.valueOf(inventoryMovement.getQuantity()), 
					inventoryMovement.getMovementType(), inventoryMovement.getReferenceType(), inventoryMovement.getCreatedBy().getUsername());
			movementDTOList.add(inventoryMovementDTO);
		}
		return movementDTOList;
	}

	@Transactional(readOnly = true)
	@Override
	public byte[] generateInvoice(String id) {
		return null;
	}

}