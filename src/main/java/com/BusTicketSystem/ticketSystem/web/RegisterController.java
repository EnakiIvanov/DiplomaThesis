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
public class RegisterController {

	@Autowired
	private UsersService usersService;

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model, HttpSession session) {
		if(usersService.Register(user)) {
			session.setAttribute("user", user);
			return "redirect:/welcome";
		}
		model.addAllAttributes(usersService.getErrors());
		return "register";
	}
}
