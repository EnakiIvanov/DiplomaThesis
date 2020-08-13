package com.BusTicketSystem.ticketSystem.logic.user;

import java.util.Map;

public interface UsersService {
	boolean Login(User user);
	
	boolean Register(User user);

	Map<String, String> getErrors();
	
	User getUser(User user);
	
	void editUserInfo(User user, String actualUserEmail);
	
	void changeUserPassword(User loggedUser, User newUser);
}
