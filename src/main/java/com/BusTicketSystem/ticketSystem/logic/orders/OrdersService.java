package com.BusTicketSystem.ticketSystem.logic.orders;

import java.util.List;

import com.BusTicketSystem.ticketSystem.logic.user.User;

public interface OrdersService {
	void getUserOrders(User user);
	
	List<Orders> getCurrentUserOrders();
	
	void deleteOrder(int idOrder);
}
