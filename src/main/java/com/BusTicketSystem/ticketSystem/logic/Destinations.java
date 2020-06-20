package com.BusTicketSystem.ticketSystem.logic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destinations {
	private String goesFrom;
	private String arrivesTo;
	private String departureTime;
	private String hourOfArrival;
	private String date;
	private float price;

}
