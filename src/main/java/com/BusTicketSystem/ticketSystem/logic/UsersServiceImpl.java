package com.BusTicketSystem.ticketSystem.logic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BusTicketSystem.ticketSystem.DatabaseAccess;

import lombok.Getter;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	DatabaseAccess dba;
	
	@Getter
	private Map<String, String> errors = new HashMap<>();
	
	@Override
	public boolean Login(User user) {
		boolean emailCheck = dba.isEmailExist("email", user.getEmail());
		
		if(emailCheck) {
			User us = dba.getUserInfo(user.getEmail());
			
			if(user.getPassword().equals(us.getPassword())) {
				 return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean Register(User user) {
		errors.clear();
		int errorCount = 0;
		boolean emailCheck = dba.isEmailExist("email", user.getEmail());
		
		if(emailCheck) {
			errorCount++;
			errors.put("emailCheck", "Имейл адреса вече съществува");
		}
		
		if(!user.getPassword().equals(user.getRePassword())) {
			errorCount++;
			errors.put("passwordCheck", "Паролите не съвпадат");
		}
		
		if(errorCount > 0) {
			return false;
		}
		
		dba.addUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhone());
		return true;
	}
	
	@Override
	public User getUser(User user) {
		User us = dba.getUserInfo(user.getEmail());
		return us;
	}
}
