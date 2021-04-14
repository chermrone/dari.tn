package tn.dari.spring.controller;


import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.Delivery;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.service.DeliveryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dari/Delivery")
public class DeliveryController {
	
	@Autowired
	DeliveryService deliveryService;
	private static final Logger log = LoggerFactory.getLogger(DeliveryController.class);
	
	

	@GetMapping("/all")
	public List<Delivery> getAllDelivery() {
		return deliveryService.getAllDelivery();
	}

	@GetMapping("/all/{id}")
	public ResponseEntity<Delivery> getFournitureAdById(@PathVariable(value = "id") Long ID)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(deliveryService.getDeliveryById(ID));
	}

	@PostMapping("/add")
	public Delivery postFournitureAd(@Valid @RequestBody Delivery delivery) {

		Delivery deliveryResult = null;
		if(delivery.getOrderUser().isStatusOrd()){
			deliveryResult = deliveryService.postDelivery(delivery);
		}else{
			log.error("Order was not payed", this);
			return null;
		}
		return deliveryResult;

	}

	@PutMapping("/modif/{id}")
	public ResponseEntity<Delivery> putFournitureAd(@PathVariable(value = "id") Long ID,
			@Valid @RequestBody Delivery delivery) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(deliveryService.putDelivery(ID, delivery));
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteDelivery(@PathVariable(value = "id") Long ID) throws ResourceNotFoundException{
		return deliveryService.deleteDelivery(ID);
	}

}
