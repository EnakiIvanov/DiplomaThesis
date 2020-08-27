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
public class HomeController {
	
	@Autowired
	private DestinationsService destService;
	
	@GetMapping("/home")
	public String home(Model model, HttpSession session) {
		User user = User.class.cast(session.getAttribute("user"));
		destService.populateDropdown();
		
		model.addAttribute("user", user);
		model.addAttribute("startingLocations", destService.getStartingLocations());
		model.addAttribute("endLocations", destService.getEndLocations());
		model.addAttribute("dest", new Destinations());
		
		return "home";
	}
	
	@PostMapping("/searchDestinations")
	public String searchDestination(@ModelAttribute Destinations dest, HttpSession session) {
		session.setAttribute("goesFrom", dest.getGoesFrom());
		session.setAttribute("arrivesTo", dest.getArrivesTo());
		session.setAttribute("date", dest.getDate());
		 
		return "redirect:/destinations";
	}

}
