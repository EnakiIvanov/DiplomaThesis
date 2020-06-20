package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.BusTicketSystem.ticketSystem.logic.Destinations;
import com.BusTicketSystem.ticketSystem.logic.DestinationsService;

@Controller
public class DestinationsController {

	@Autowired
	private DestinationsService destService;
	
	@GetMapping("/destinations")
	public String destinations(Model model, HttpSession session) {
		destService.updateTable(session);
		
		model.addAttribute("allDestinations", destService.getAllDestinations());
		model.addAttribute("startingLocations", destService.getStartingLocations());
		model.addAttribute("endLocations", destService.getEndLocations());
		model.addAttribute("dest", new Destinations());

		return "destinations";
	}

	@PostMapping("/destinations")
	public String postDestinations(@ModelAttribute Destinations dest, Model model, HttpSession session) {
		destService.updateTable(destService.updateSession(session, dest));

		model.addAttribute("allDestinations", destService.getAllDestinations());
		model.addAttribute("startingLocations", destService.getStartingLocations());
		model.addAttribute("endLocations", destService.getEndLocations());
		model.addAttribute("dest", new Destinations());
		return "destinations";
	}
	
	@PostMapping("/purchase")
	public String postPurchase(@ModelAttribute Destinations dest, HttpSession session) {
		session.setAttribute("ChoosenDestination", dest);
		return "redirect:/purchase";
	}
}
