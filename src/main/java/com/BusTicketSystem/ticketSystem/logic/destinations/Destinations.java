package com.BusTicketSystem.ticketSystem.logic.destinations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destinations {
	private int idDest;
	private String goesFrom;
	private String arrivesTo;
	private String departureTime;
	private String hourOfArrival;
	private String date;
	private float price;

}
