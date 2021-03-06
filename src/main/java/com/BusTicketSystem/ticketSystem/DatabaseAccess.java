package com.BusTicketSystem.ticketSystem;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.BusTicketSystem.ticketSystem.logic.user.User;

@Service
public class DatabaseAccess {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addUser(String firstName, String lastName, String email, String password, String phone) {
		jdbcTemplate.update("INSERT INTO users(first_name, last_name, email, password, phone) VALUE (?,?,?,?,?)",
				new Object[] {firstName, lastName, email, password, phone});
	}
	
	public void addTemporaryUser(User user) {
		jdbcTemplate.update("INSERT INTO temporary_user(first_name, last_name, email, phone) VALUE (?,?,?,?)",
				new Object[] {user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone()});
	}
	
	public void addOrder(int idUser, int idDest, int idPayMethd) {
		jdbcTemplate.update("INSERT INTO orders(id_users, id_destinations, id_payment_method, purchase_date) VALUE (?,?,?,NOW())",
				new Object[] {idUser, idDest, idPayMethd});
	}
	
	public void addOrderTempUser(int idDest, int idPayMethd, int idUser) {
		jdbcTemplate.update("INSERT INTO orders(id_destinations, id_payment_method, purchase_date, id_temporary_user) VALUE (?,?,NOW(),?)",
				new Object[] {idDest, idPayMethd, idUser});
	}
	
	public void addDestinations(String goesFrom, String arrivesTo, String departureTime, String hourOfArrival, String price) {
		jdbcTemplate.update("INSERT INTO destinations(goes_from, arrives_to, departure_time, hour_of_arrival, price) VALUE (?,?,?,?,?)",			
				new Object[] {goesFrom, arrivesTo, departureTime, hourOfArrival, price});
	}
	
	public int getPaymentMethodId(String MethodName) {
		String sql = "SELECT id_payment_method FROM payment_method WHERE method_name = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {MethodName} , Integer.TYPE);
	}
	
	public int getTempUserId(String email) {
		String sql = "SELECT id_temporary_user FROM temporary_user WHERE email = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {email} , Integer.TYPE);
	}
	
	public int getUserId(String email) {
		String sql = "SELECT id_users FROM users WHERE email = ?";
		
		return jdbcTemplate.queryForObject(sql, new Object[] {email} , Integer.TYPE);
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
					rs.getInt("id_users"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("email"),
					rs.getString("phone"),
					rs.getString("password")
				));
		
		//return (User) jdbcTemplate.queryForObject(sql, new Object[] {email}, new BeanPropertyRowMapper(User.class));
	}
	
	public List<Map<String, Object>> getAllPaymentMethods() {
		String sql = "SELECT method_name FROM payment_method";
		
		return jdbcTemplate.queryForList(sql);
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
		String sql = "SELECT id_destinations, goes_from, arrives_to, DATE_FORMAT(departure_time, '%H:%i') as departure_time, DATE_FORMAT(hour_of_arrival, '%H:%i') as hour_of_arrival, price \r\n" + 
				"FROM destinations \r\n" + 
				"WHERE goes_from = ? AND arrives_to = ? AND DATE_FORMAT(departure_time, '%d/%m/%Y') = ? ";
		return jdbcTemplate.queryForList(sql, new Object[] {goes_from, arrives_to, departure_time});
	}

	public void updateUser(String firstName, String lastName, String email, String phone, int idUser) {
		String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id_users = ?";
		
		jdbcTemplate.update(sql, new Object[] {firstName, lastName, email, phone, idUser});
	}
	
	public void updateUserPassword(String password, int idUser) {
		String sql = "UPDATE users SET password = ? WHERE id_users = ?";
		
		jdbcTemplate.update(sql, new Object[] {password, idUser});
	}
	
	public List<Map<String, Object>> getAllOrders(int idUser){
		String sql = "SELECT o.id_orders, d.goes_from, d.arrives_to, d.departure_time, d.hour_of_arrival, d.price, o.purchase_date " + 
					 "FROM Orders o JOIN Destinations d ON o.id_destinations = d.id_destinations " + 
					 "WHERE o.id_users = ?";
		
		return jdbcTemplate.queryForList(sql, new Object[] {idUser});
	}
	
	public void deleteOrder(int idOrder) {
		String sql = "DELETE FROM orders WHERE id_orders = ?";
		
		jdbcTemplate.update(sql, new Object[] {idOrder});
	}
}
