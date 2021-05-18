package tn.dari.spring.service;

import java.util.List;
import java.util.Map;

import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.exception.ResourceNotFoundException;



public interface IShoppingCartService {
	public List<ShoppingCart> getAllShoppingCart();

	public ShoppingCart getShoppingCartById(Long faID) throws ResourceNotFoundException;

	public ShoppingCart postShoppingCart(ShoppingCart shoppingCart);

	public ShoppingCart putShoppingCart(Long ID, ShoppingCart shoppingCart) throws ResourceNotFoundException;

	public Map<String, Boolean> deleteShoppingCart(Long ID) throws ResourceNotFoundException;

	public ShoppingCart getShoppingCartByUsername(String username) throws ResourceNotFoundException;

}
