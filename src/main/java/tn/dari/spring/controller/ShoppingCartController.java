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

import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.service.ShoppingCartService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dari/ShoppingCart")
public class ShoppingCartController {
	@Autowired
	ShoppingCartService shoppingCartService;

	private static final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);
	
	@GetMapping("/all")
	public List<ShoppingCart> getAllShoppingCart() {
		return shoppingCartService.getAllShoppingCart();
	}

	@GetMapping("/all/{id}")
	public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable(value = "id") Long faID)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(shoppingCartService.getShoppingCartById(faID));
	}

	@GetMapping("/byUsername/{username}")
	public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable(value = "username") String username)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(shoppingCartService.getShoppingCartByUsername(username));
	}

	@PostMapping("/add")
	public ShoppingCart postShoppingCart(@Valid @RequestBody ShoppingCart shoppingCart) {
		return shoppingCartService.postShoppingCart(shoppingCart);

	}

	@PutMapping("/modif/{id}")
	public ResponseEntity<ShoppingCart> putShoppingCart(@PathVariable(value = "id") Long ID,
			@Valid @RequestBody ShoppingCart shoppingCart) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(shoppingCartService.putShoppingCart(ID, shoppingCart));
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteShoppingCart(@PathVariable(value = "id") Long ID) throws ResourceNotFoundException{
		return shoppingCartService.deleteShoppingCart(ID);
	}

}
