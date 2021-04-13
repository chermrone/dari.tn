package tn.dari.spring.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Delivery;
import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.DeliveryRepository;

@Service
public class DeliveryService implements IDeliveryService{
	
	@Autowired
	DeliveryRepository deliveryRepository;
	
	@Override
	public List<Delivery> getAllDelivery() {

		return deliveryRepository.findAll();
	}

	@Override
	public Delivery getDeliveryById(Long ID) throws ResourceNotFoundException {

		Delivery delivery = deliveryRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		return delivery;
	}

	@Override
	public Delivery postDelivery(Delivery delivery) {
		
		deliveryRepository.save(delivery);
		return delivery;
	}

	@Override
	public Delivery putDelivery(Long ID, Delivery delivery) throws ResourceNotFoundException {
		if (delivery.getDeliveryId() == ID) {
			Delivery delivery1 = deliveryRepository.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));

			
			deliveryRepository.save(delivery);
		}
		return delivery;
	}

	@Override
	public Map<String, Boolean> deleteDelivery(Long ID) throws ResourceNotFoundException {
		Delivery delivery = deliveryRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		deliveryRepository.delete(delivery);
		Map<String, Boolean> response = new HashMap<>();
		response.put("FournitureAd deleted : ", Boolean.TRUE);
		return response;
	}

}
