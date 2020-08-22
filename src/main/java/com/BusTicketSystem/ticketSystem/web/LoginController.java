package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.BusTicketSystem.ticketSystem.logic.user.User;
import com.BusTicketSystem.ticketSystem.logic.user.UsersService;


@Controller
public class LoginController {
	
	@Autowired
	private UsersService usersService;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
		if(usersService.Login(user)) {
			User us = usersService.getUser(user);
			session.setAttribute("user", us);
			return "redirect:/welcome";
		}
		
		model.addAttribute("displayError", "Грешен имейл или парола");	
		return "login";
	}
}
