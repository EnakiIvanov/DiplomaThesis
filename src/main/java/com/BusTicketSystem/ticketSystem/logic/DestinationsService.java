package com.BusTicketSystem.ticketSystem.logic;

import java.util.List;

import javax.servlet.http.HttpSession;

public interface DestinationsService {

	void updateTable(HttpSession session);
	
	HttpSession updateSession(HttpSession session, Destinations dest);

	List<Destinations> getAllDestinations();
	
	List<String> getStartingLocations();
	
	List<String> getEndLocations();
	
	void populateDropdown();
}
