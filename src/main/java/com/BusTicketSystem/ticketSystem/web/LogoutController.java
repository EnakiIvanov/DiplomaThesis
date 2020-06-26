package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}

	@RequestMapping("/logoutDest")
	public String logoutDest(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/destinations";
	}
}
