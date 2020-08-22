package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.BusTicketSystem.ticketSystem.logic.user.User;


@Controller
public class WelcomeController {

	@GetMapping("/welcome")
	public String profile(Model model, HttpSession session) {
		User user = User.class.cast(session.getAttribute("user"));
		
		model.addAttribute("user", user);
		
		return "welcome";
	}
	
	@PostMapping("/bus")
	public String bus() {
		
		return "redirect:/home";
	}
}
