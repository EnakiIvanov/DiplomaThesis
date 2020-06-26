package com.BusTicketSystem.ticketSystem.logic.purchase;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.BusTicketSystem.ticketSystem.logic.destinations.Destinations;
import com.BusTicketSystem.ticketSystem.logic.user.User;

public interface PurchaseService {
	void Purchase(Destinations dest, User user, HttpSession session);
	
	List<String> getPaymentMethods();
	
	void PopulatePaymentMethodList();
}
