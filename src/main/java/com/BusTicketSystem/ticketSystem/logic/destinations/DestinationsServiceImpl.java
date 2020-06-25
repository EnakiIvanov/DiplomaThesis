package com.BusTicketSystem.ticketSystem.logic.destinations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BusTicketSystem.ticketSystem.DatabaseAccess;

import lombok.Getter;

@Service
public class DestinationsServiceImpl implements DestinationsService {
	
	@Autowired
	DatabaseAccess dba;
	
	@Getter
	private List<Destinations> allDestinations = new ArrayList<Destinations>();
	@Getter
	private List<String> startingLocations = new ArrayList<String>();
	@Getter
	private List<String> endLocations = new ArrayList<String>();

	@Override
	public void updateTable(HttpSession session) {
		populateDropdown();
		allDestinations.clear();
		
		String goesFrom = String.valueOf(session.getAttribute("goesFrom"));
		String arriveTo = String.valueOf(session.getAttribute("arrivesTo"));
		String date = String.valueOf(session.getAttribute("date"));
		
		List<Map<String, Object>> all_dests = dba.getAllLocations(goesFrom, arriveTo, date);
		
		all_dests.forEach(item -> {
			Destinations dest = new Destinations();
			
			item.forEach((k,v) -> {
				switch(k) {
				case "id_destinations":
					dest.setIdDest(Integer.valueOf(String.valueOf(v)));
					break;
				case "goes_from": 
					dest.setGoesFrom(String.valueOf(v));
					break;
				case "arrives_to":
					dest.setArrivesTo(String.valueOf(v));
					break;
				case "departure_time": 
					dest.setDepartureTime(String.valueOf(v));
					break;
				case "hour_of_arrival":
					dest.setHourOfArrival(String.valueOf(v));
					break;
				case "price":
					dest.setPrice(Float.valueOf(String.valueOf(v)));
					break;
				}
			});
			dest.setDate(date);
			allDestinations.add(dest);
		});
	}

	@Override
	public void populateDropdown() {
		startingLocations.clear();
		endLocations.clear();
		List<Map<String, Object>> all_StartLocs = dba.getAllStartLocations();
		all_StartLocs.stream().distinct().forEach(item -> item.forEach((k,v) -> startingLocations.add(String.valueOf(v))));

		List<Map<String, Object>> all_EndLocs = dba.getAllEndLocations();
		all_EndLocs.stream().distinct().forEach(item -> item.forEach((k,v) -> endLocations.add(String.valueOf(v))));
	}

	@Override
	public HttpSession updateSession(HttpSession session, Destinations dest) {
		 session.setAttribute("goesFrom", dest.getGoesFrom());
		 session.setAttribute("arrivesTo", dest.getArrivesTo());
		 session.setAttribute("date", dest.getDate());
		 return session;
	}
}
