package com.BusTicketSystem.ticketSystem.logic;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
	@NonNull
	private String firstName;
	@NonNull 
	private String lastName;
	@NonNull 
	private String email;
	@NonNull 
	private String phone;
	@NonNull 
	private String password;
	private String rePassword;
}
