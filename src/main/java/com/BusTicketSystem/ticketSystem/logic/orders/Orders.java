package com.BusTicketSystem.ticketSystem.logic.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
	private int idOrder;
	private String goesFrom;
	private String arrivesTo;
	private String departureTime;
	private String hourOfArrival;
	private String purchaseDate;
	private float price;
}
