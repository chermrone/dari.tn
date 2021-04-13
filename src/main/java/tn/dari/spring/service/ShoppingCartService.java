package tn.dari.spring.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService implements IShoppingCartService{
	
	@Autowired
	ShoppingCartRepository shoppingCartRepository;

	@Override
	public List<ShoppingCart> getAllShoppingCart() {
		
		return shoppingCartRepository.findAll();
	}

	@Override
	public ShoppingCart getShoppingCartById(Long faID) throws ResourceNotFoundException {
		
		ShoppingCart shoppingCart = shoppingCartRepository.findById(faID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + faID));
		return shoppingCart;
	}

	@Override
	public ShoppingCart postShoppingCart(ShoppingCart shoppingCart) {
	
		shoppingCartRepository.save(shoppingCart);
		return shoppingCart;
	}

	@Override
	public ShoppingCart putShoppingCart(Long ID, ShoppingCart shoppingCart) throws ResourceNotFoundException {
		if (shoppingCart.getShoppingCartId() == ID) {
			ShoppingCart shoppingCart1 = shoppingCartRepository.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));

			
			shoppingCartRepository.save(shoppingCart);
		}
		return shoppingCart;
	}

	@Override
	public Map<String, Boolean> deleteShoppingCart(Long ID) throws ResourceNotFoundException {
		ShoppingCart shoppingCart = shoppingCartRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		shoppingCartRepository.delete(shoppingCart);
		Map<String, Boolean> response = new HashMap<>();
		response.put("FournitureAd deleted : ", Boolean.TRUE);
		return response;
	}
	

}
