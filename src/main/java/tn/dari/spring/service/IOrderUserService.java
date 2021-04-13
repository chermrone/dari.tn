package tn.dari.spring.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.dari.spring.dto.DailyProfit;
import tn.dari.spring.entity.OrderUser;
import tn.dari.spring.exception.ResourceNotFoundException;

public interface IOrderUserService {
	
	public List<OrderUser> getAllOrder();

	public OrderUser getOrderById(Long ID) throws ResourceNotFoundException;

	public OrderUser postOrder(OrderUser order);

	public OrderUser putOrder(Long ID, OrderUser order) throws ResourceNotFoundException;

	public Map<String, Boolean> deleteOrder(Long ID) throws ResourceNotFoundException;
	
	public Float getTotalProfit(Date dateDebut , Date dateFin );
	public  List<DailyProfit>  getDailyProfit(LocalDate dateDebut , LocalDate dateFin );

}
