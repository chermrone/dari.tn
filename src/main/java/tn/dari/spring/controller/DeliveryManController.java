package tn.dari.spring.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import tn.dari.spring.entity.DeliveryMan;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.service.DeliveryManService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dari/DeliveryMan")
public class DeliveryManController {
	
	@Autowired
	DeliveryManService deliveryManService;
	
	@GetMapping("/all")
	public List<DeliveryMan> getAllDeliveryMan() {
		
		return deliveryManService.getAllDeliveryMan();
		
	}

	@GetMapping("/all/{id}")
	public ResponseEntity<DeliveryMan> getDeliveryManById(@PathVariable(value = "id") Long ID)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(deliveryManService.getDeliveryManById(ID));
	}

	@PostMapping("/add")
	public DeliveryMan postDeliveryMan(@Valid @RequestBody DeliveryMan deliveryMan) {
		return deliveryManService.postDeliveryMan(deliveryMan);

	}

	@PutMapping("/modif/{id}")
	public ResponseEntity<DeliveryMan> putDeliveryMan(@PathVariable(value = "id") Long ID,
			@Valid @RequestBody DeliveryMan deliveryMan) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(deliveryManService.putDeliveryMan(ID, deliveryMan));
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteDeliveryMan(@PathVariable(value = "id") Long ID) throws ResourceNotFoundException{
		return deliveryManService.deleteDeliveryMan(ID);
	}

}
