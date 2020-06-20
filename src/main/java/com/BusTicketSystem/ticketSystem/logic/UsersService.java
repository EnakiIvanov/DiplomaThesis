package com.BusTicketSystem.ticketSystem.logic;

import java.util.Map;

public interface UsersService {
	boolean Login(User user);
	
	boolean Register(User user);

	Map<String, String> getErrors();
	
	User getUser(User user);
}
