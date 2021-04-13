package tn.dari.spring.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.DeliveryMan;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.DeliveryManRepository;

@Service
public class DeliveryManService implements IDeliveryManService{
	
	@Autowired
	DeliveryManRepository deliveryManRepository;

	@Override
	public List<DeliveryMan> getAllDeliveryMan() {

		return deliveryManRepository.findAll();
	}

	@Override
	public DeliveryMan getDeliveryManById(Long ID) throws ResourceNotFoundException {
		DeliveryMan deliveryMan = deliveryManRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		return deliveryMan;
	}

	@Override
	public DeliveryMan postDeliveryMan(DeliveryMan deliveryMan) {


		deliveryManRepository.save(deliveryMan);
		return deliveryMan;
	}

	@Override
	public DeliveryMan putDeliveryMan(Long ID, DeliveryMan deliveryMan) throws ResourceNotFoundException {
		if (deliveryMan.getDeliveryManID() == ID) {
			DeliveryMan deliveryMan1 = deliveryManRepository.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));

			
			deliveryManRepository.save(deliveryMan);
		}
		return deliveryMan;
	}

	@Override
	public Map<String, Boolean> deleteDeliveryMan(Long ID) throws ResourceNotFoundException {
		DeliveryMan deliveryMan = deliveryManRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		deliveryManRepository.delete(deliveryMan);
		Map<String, Boolean> response = new HashMap<>();
		response.put("FournitureAd deleted : ", Boolean.TRUE);
		return response;
	}

}
