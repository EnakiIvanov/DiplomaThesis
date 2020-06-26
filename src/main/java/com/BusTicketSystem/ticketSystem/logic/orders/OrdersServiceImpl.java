package com.BusTicketSystem.ticketSystem.logic.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BusTicketSystem.ticketSystem.DatabaseAccess;
import com.BusTicketSystem.ticketSystem.logic.user.User;

import lombok.Getter;

@Service
public class OrdersServiceImpl implements OrdersService {
	
	@Autowired
	DatabaseAccess dba;
	
	@Getter
	private List<Orders> currentUserOrders = new ArrayList<Orders>();

	@Override
	public void getUserOrders(User user) {
		int idUser = user.getIdUser();
		currentUserOrders.clear();
		
		List<Map<String, Object>> current_user_orders = dba.getAllOrders(idUser);
		
		current_user_orders.forEach(item -> {
			Orders order = new Orders();
 
			item.forEach((k,v) -> {
				switch(k) {
				case "id_orders":
					order.setIdOrder(Integer.valueOf(String.valueOf(v)));
					break;
				case "goes_from": 
					order.setGoesFrom(String.valueOf(v));
					break;
				case "arrives_to":
					order.setArrivesTo(String.valueOf(v));
					break;
				case "departure_time": 
					order.setDepartureTime(String.valueOf(v));
					break;
				case "hour_of_arrival":
					order.setHourOfArrival(String.valueOf(v));
					break;
				case "price":
					order.setPrice(Float.valueOf(String.valueOf(v)));
					break;
				case "purchase_date":
					order.setPurchaseDate(String.valueOf(v));
					break;
				}
			});
			currentUserOrders.add(order);
		});
	}

	@Override
	public void deleteOrder(int idOrder) {
		dba.deleteOrder(idOrder);	
	}

}
