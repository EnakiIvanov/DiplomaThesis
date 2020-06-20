package com.BusTicketSystem.ticketSystem;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.BusTicketSystem.ticketSystem.logic.User;

@Service
public class DatabaseAccess {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addUser(String firstName, String lastName, String email, String password, String phone) {
		jdbcTemplate.update("INSERT INTO users(first_name, last_name, email, password, phone) VALUE (?,?,?,?,?)",
				new Object[] {firstName, lastName, email, password, phone});
		
	}
	
	public boolean isEmailExist(String field, String value) {
		String sql = "SELECT COUNT(?) FROM users WHERE email = ?";
		
		Integer users = jdbcTemplate.queryForObject(sql, new Object[] {field, value}, Integer.class);

		return users != null && users > 0 ? true : false;
	}
	
	public User getUserInfo(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {email}, (rs, rowNum) ->
			new User(
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("email"),
					rs.getString("phone"),
					rs.getString("password")
				));
		
		//return (User) jdbcTemplate.queryForObject(sql, new Object[] {email}, new BeanPropertyRowMapper(User.class));
	}
	
	public List<Map<String, Object>> getAllStartLocations() {
		String sql = "SELECT goes_from FROM destinations";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> getAllEndLocations() {
		String sql = "SELECT arrives_to FROM destinations";
		
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> getAllLocations(String goes_from, String arrives_to, String departure_time) {
		String sql = "SELECT goes_from, arrives_to, DATE_FORMAT(departure_time, '%H:%i') as departure_time, DATE_FORMAT(hour_of_arrival, '%H:%i') as hour_of_arrival, price \r\n" + 
				"FROM destinations \r\n" + 
				"WHERE goes_from = ? AND arrives_to = ? AND DATE_FORMAT(departure_time, '%d/%m/%Y') = ? ";
		return jdbcTemplate.queryForList(sql, new Object[] {goes_from, arrives_to, departure_time});
	}

}
