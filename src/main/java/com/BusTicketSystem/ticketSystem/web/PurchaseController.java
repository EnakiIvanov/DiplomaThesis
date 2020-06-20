package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.BusTicketSystem.ticketSystem.logic.Destinations;
import com.BusTicketSystem.ticketSystem.logic.User;

@Controller
public class PurchaseController {

	@GetMapping("/purchase")
	public String purchase(Model model, HttpSession session) {
		Destinations dest = Destinations.class.cast(session.getAttribute("ChoosenDestination"));

		model.addAttribute("dest", dest);
		model.addAttribute("user", new User());
		return "purchase";
	}
	
	@PostMapping("/finishPurchase")
    public String postPurchase(@ModelAttribute User user, Model model) {
	
		return "redirect:/";
	}
}
