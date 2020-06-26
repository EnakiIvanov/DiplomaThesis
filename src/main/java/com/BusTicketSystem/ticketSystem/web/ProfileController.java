package com.BusTicketSystem.ticketSystem.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.BusTicketSystem.ticketSystem.logic.orders.Orders;
import com.BusTicketSystem.ticketSystem.logic.orders.OrdersService;
import com.BusTicketSystem.ticketSystem.logic.user.User;
import com.BusTicketSystem.ticketSystem.logic.user.UsersService;

@Controller
public class ProfileController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private OrdersService ordersService;
	
	@GetMapping("/profile")
	public String profile(Model model, HttpSession session) {
		User user = User.class.cast(session.getAttribute("user"));
		ordersService.getUserOrders(user);
		
		model.addAttribute("user", user);
		model.addAttribute("CurrentUserOrders", ordersService.getCurrentUserOrders());
		model.addAttribute("order", new Orders());
		return "profile";
	}
	
	@PostMapping("/profile/edit")
	public String editProfile(@ModelAttribute User user, Model model, HttpSession session) {
		User loggedUser = User.class.cast(session.getAttribute("user"));
		usersService.editUserInfo(user, loggedUser.getEmail());
		User EditedUser = usersService.getUser(user);
		session.setAttribute("user", EditedUser);

		return "redirect:/profile";
	}
	
	@PostMapping("/profile/delete")
	public String orderDetails(@ModelAttribute Orders order, HttpSession session, Model model) {
		ordersService.deleteOrder(order.getIdOrder());
		
		return "redirect:/profile";
	}
}