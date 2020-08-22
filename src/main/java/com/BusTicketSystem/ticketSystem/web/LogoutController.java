package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	@RequestMapping("/logoutHome")
	public String logoutHome(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/home";
	}
	
	@RequestMapping("/logoutWelcome")
	public String logoutWelcome(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/welcome";
	}

	@RequestMapping("/logoutDest")
	public String logoutDest(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/destinations";
	}
}
