package tn.dari.spring.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.OrderUser;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.OrderUserRepository;

@Service
public class OrderUserService implements IOrderUserService{
	
	@Autowired
	OrderUserRepository orderUserRepository;
	@Override
	public List<OrderUser> getAllOrder() {
		
		return orderUserRepository.findAll();
		
	}

	@Override
	public OrderUser getOrderById(Long ID) throws ResourceNotFoundException {

		OrderUser order = orderUserRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		return order;
	}

	@Override
	public OrderUser postOrder(OrderUser order) {
		Date date = new Date();
		order.setDateCreated(date);
		orderUserRepository.save(order);
		return order;
	}

	@Override
	public OrderUser putOrder(Long ID, OrderUser order) throws ResourceNotFoundException {
		if (order.getOrderId() == ID) {
			OrderUser order1 = orderUserRepository.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));

			Date date = new Date();
			order.setDateCreated(date);
			orderUserRepository.save(order);
		}
		return order;
	}

	@Override
	public Map<String, Boolean> deleteOrder(Long ID) throws ResourceNotFoundException {
		OrderUser order = orderUserRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		orderUserRepository.delete(order);
		Map<String, Boolean> response = new HashMap<>();
		response.put("FournitureAd deleted : ", Boolean.TRUE);
		return response;
	}


}
