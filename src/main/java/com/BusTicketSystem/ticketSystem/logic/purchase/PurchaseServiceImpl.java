package com.BusTicketSystem.ticketSystem.logic.purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BusTicketSystem.ticketSystem.DatabaseAccess;
import com.BusTicketSystem.ticketSystem.logic.destinations.Destinations;
import com.BusTicketSystem.ticketSystem.logic.user.User;

import lombok.Getter;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	DatabaseAccess dba;
	
	@Getter
	private List<String> paymentMethods = new ArrayList<String>();
	
	@Override
	public void Purchase(Destinations dest, User user, HttpSession session) {
		User us = User.class.cast(session.getAttribute("user"));
		int idPaymentMethod = dba.getPaymentMethodId(session.getAttribute("methodName").toString());
		if(us == null){
			dba.addTemporaryUser(user);
			int tempUserId = dba.getTempUserId(user.getEmail());
			dba.addOrder(tempUserId, dest.getIdDest(), idPaymentMethod);
		}else if(us != null) {
			dba.addOrder(us.getIdUser(), dest.getIdDest(), idPaymentMethod);
		}
	}
	
	@Override
	public void PopulatePaymentMethodList() {
		paymentMethods.clear();
		List<Map<String, Object>> all_pm = dba.getAllPaymentMethods();
		all_pm.stream().distinct().forEach(item -> item.forEach((k,v) -> paymentMethods.add(String.valueOf(v))));

	}

}
