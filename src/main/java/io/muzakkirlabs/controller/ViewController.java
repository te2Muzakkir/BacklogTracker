package io.muzakkirlabs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {
	
	@GetMapping("/view")
	public String view(@RequestParam("viewName") String viewName) {
		return "spa/"+viewName;
	}

}