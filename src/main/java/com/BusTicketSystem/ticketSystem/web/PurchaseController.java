package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.BusTicketSystem.ticketSystem.logic.destinations.Destinations;
import com.BusTicketSystem.ticketSystem.logic.purchase.PaymentMethod;
import com.BusTicketSystem.ticketSystem.logic.purchase.PurchaseService;
import com.BusTicketSystem.ticketSystem.logic.user.User;

@Controller
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	@GetMapping("/purchase")
	public String purchase(Model model, HttpSession session) {
		Destinations dest = Destinations.class.cast(session.getAttribute("ChoosenDestination"));
		User user = User.class.cast(session.getAttribute("user"));
		purchaseService.PopulatePaymentMethodList();
		
		model.addAttribute("paym", new PaymentMethod());
		model.addAttribute("paymentMethods", purchaseService.getPaymentMethods());
		model.addAttribute("user", user != null ? user : new User());
		model.addAttribute("dest", dest);
		
		return "purchase";
	}
	
	@GetMapping("/purchase/payment")
	public String purchasePayment(@ModelAttribute PaymentMethod paym, HttpSession session, Model model, 
			@RequestParam(value="methodName",required=false) String methodName,
			@ModelAttribute User user) {
		Destinations dest = Destinations.class.cast(session.getAttribute("ChoosenDestination"));
		purchaseService.PopulatePaymentMethodList();	
		
		if(methodName.equalsIgnoreCase("кредитна карта")) { 
			paym.setMethodName("CreditCard");
		}else{
			paym.setMethodName(methodName);
		}
		
		
		model.addAttribute("paym", paym);
		model.addAttribute("user", user);
		model.addAttribute("dest", dest);
		
		return "purchase";
	}
	
	@PostMapping("/finishPurchase")
    public String postPurchase(@ModelAttribute User user, HttpSession session) {
		Destinations dest = Destinations.class.cast(session.getAttribute("ChoosenDestination"));
		purchaseService.Purchase(dest, user, session);
		return "redirect:/";
	}
}
