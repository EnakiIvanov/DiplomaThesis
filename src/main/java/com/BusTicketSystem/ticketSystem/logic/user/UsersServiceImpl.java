package com.BusTicketSystem.ticketSystem.logic.user;

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
	
	@Override
	public void editUserInfo(User user, String actualUserEmail) {
		int idUser = dba.getUserId(actualUserEmail);
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String email = user.getEmail();
		String phone = user.getPhone();
		
		dba.updateUser(firstName, lastName, email, phone, idUser);
	}

	
	@Override
	public void changeUserPassword(User loggedUser, User newUser) {
		errors.clear();
		if(newUser.getOldPassword().equals(loggedUser.getPassword())) {
			if(newUser.getPassword().equals(newUser.getRePassword())) {
				dba.updateUserPassword(newUser.getPassword(), loggedUser.getIdUser());
			}else {
				errors.put("passwordCheck2", "Паролите не съвпадат");
			}
		}else {
			errors.put("passwordCheck", "Грешна парола");
		}
	}
}
