package com.BusTicketSystem.ticketSystem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BusTicketSystem.ticketSystem.logic.bot.Bot;

@RestController
public class BotController {

	@Autowired
	private Bot bot;
	
	@RequestMapping("/populateDatabase")
	public String populateDB() {
		bot.getInfo();
		bot.populateDB();
		return "Database successful populated";
	}
}
