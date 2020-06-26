package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.BusTicketSystem.ticketSystem.logic.destinations.Destinations;
import com.BusTicketSystem.ticketSystem.logic.destinations.DestinationsService;
import com.BusTicketSystem.ticketSystem.logic.user.User;

@Controller
public class DestinationsController {

	@Autowired
	private DestinationsService destService;
	
	@GetMapping("/destinations")
	public String destinations(Model model, HttpSession session) {
		User user = User.class.cast(session.getAttribute("user"));
		destService.updateTable(session);
		
		model.addAttribute("user", user);
		model.addAttribute("allDestinations", destService.getAllDestinations());
		model.addAttribute("startingLocations", destService.getStartingLocations());
		model.addAttribute("endLocations", destService.getEndLocations());
		model.addAttribute("dest", new Destinations());

		return "destinations";
	}

	@PostMapping("/destinations/search")
	public String postDestinations(@ModelAttribute Destinations dest, HttpSession session) {
		destService.updateSession(session, dest);;
		return "redirect:/destinations";
	}
	
	@PostMapping("/purchase")
	public String postPurchase(@ModelAttribute Destinations dest, HttpSession session) {
		session.setAttribute("ChoosenDestination", dest);
		return "redirect:/purchase";
	}
	
}
