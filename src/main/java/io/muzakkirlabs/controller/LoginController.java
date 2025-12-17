package io.muzakkirlabs.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.muzakkirlabs.service.BacklogTrackerService;
import io.muzakkirlabs.service.PasswordResetTokenService;
import io.muzakkirlabs.service.UsersSerivce;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private UsersSerivce usersSerivce;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
	@Autowired
	private BacklogTrackerService backlogTrackerService;
	
	@GetMapping("/login")
	public String landingPage() {
		return "landingPage";
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("username", backlogTrackerService.getLoggedInUserName());
		Map<String, String> valueMap = backlogTrackerService.getdashboardDetails();
		model.addAttribute("totalInventoryCount", valueMap.get("totalInventoryCount"));
		model.addAttribute("lowInventoryCount", valueMap.get("lowInventoryCount"));
		model.addAttribute("pendingOrderCount", valueMap.get("pendingOrderCount"));
		model.addAttribute("poCreatedTodayCount", valueMap.get("poCreatedTodayCount"));
		return "home";
	}
	
	@PostMapping("/register")
	public String regiser(HttpServletRequest request, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("email") String email) {
		usersSerivce.create(username, password, email);
		
	    UsernamePasswordAuthenticationToken authToken =
	            new UsernamePasswordAuthenticationToken(email, password);

	    Authentication authentication = authenticationManager.authenticate(authToken);
	    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
	    securityContext.setAuthentication(authentication);
	    SecurityContextHolder.setContext(securityContext);
	    HttpSession session = request.getSession(true);
	    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		return "home";
	}
	
	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}
	
	@GetMapping("/sendForgotPasswordMail")
	public String sendForgotPasswordMail(@RequestParam("email") String email, Model model) {
		String message = passwordResetTokenService.createResetPasswordEmail(email);
		model.addAttribute("message", message);
		return "forgotPassword";
	}
	
	@GetMapping("/updatePasswordScreen")
	public String updatePasswordScreen(@RequestParam("token") String token, Model model) {
		model.addAttribute("token", token);
		return "updatePassword";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam("token") String token, 
			@RequestParam("password") String password, Model model) {
		String message = passwordResetTokenService.updatePassword(token, password);
		model.addAttribute("message", message);
		return "updatePassword";
	}
	
	@GetMapping("/ping")
	public @ResponseBody String ping() {
	    return "OK";
	}
	
}