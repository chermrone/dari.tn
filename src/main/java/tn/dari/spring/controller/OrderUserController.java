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

import tn.dari.spring.entity.OrderUser;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.service.OrderUserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/Order")
public class OrderUserController {
	@Autowired
	OrderUserService orderUserService;

	@GetMapping("/all")
	public List<OrderUser> getAllOrder() {
		return orderUserService.getAllOrder();
	}

	@GetMapping("/all/{id}")
	public ResponseEntity<OrderUser> getOrderById(@PathVariable(value = "id") Long ID) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(orderUserService.getOrderById(ID));
	}

	@PostMapping("/add")
	public OrderUser postOrder(@Valid @RequestBody OrderUser order) {
		return orderUserService.postOrder(order);

	}

	@PutMapping("/modif/{id}")
	public ResponseEntity<OrderUser> putOrder(@PathVariable(value = "id") Long ID, @Valid @RequestBody OrderUser order)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(orderUserService.putOrder(ID, order));
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteOrder(@PathVariable(value = "id") Long ID) throws ResourceNotFoundException {
		return orderUserService.deleteOrder(ID);
	}

}
