package tn.dari.spring.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.controller.CheckoutController;
import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService implements IShoppingCartService {

	@Autowired
	ShoppingCartRepository shoppingCartRepository;

	private static final Logger log = LoggerFactory.getLogger(ShoppingCartService.class);

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
		try {
			List<ShoppingCart> list = shoppingCartRepository.findAll();
			Set<FournitureAd> newlist = shoppingCart.getFournitureAds();
			for (FournitureAd ad : shoppingCart.getFournitureAds())
			{ 
				for(ShoppingCart sh : list)
				{	log.info("list"+sh.getFournitureAds().toString());
					for(FournitureAd f : sh.getFournitureAds())
					if(f.getFaID()==ad.getFaID())
						
						newlist.remove(ad);
										
				}
							
			}
					
			shoppingCart.setFournitureAds(newlist);
			for(FournitureAd d : newlist){log.info("newlist"+d.toString());}
			if(!shoppingCart.getFournitureAds().isEmpty())
				{shoppingCartRepository.save(shoppingCart);}
			else{log.info("list vide");}
		} catch (Exception e) {
			if (e.getClass().equals(SQLIntegrityConstraintViolationException.class))
				log.error("fournitureAd exists in a shoppingcart");
			

		}

		return shoppingCart;
	}

	@Override
	public ShoppingCart putShoppingCart(Long ID, ShoppingCart shoppingCart) throws ResourceNotFoundException {
		if (shoppingCart.getShoppingCartId() == ID) {
			ShoppingCart shoppingCart1 = shoppingCartRepository.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));}		
		
		try {
			List<ShoppingCart> list = shoppingCartRepository.findAll();
			for(ShoppingCart x : list){
				if(x.getShoppingCartId()==shoppingCart.getShoppingCartId())
					list.remove(x);
			}
			
			Set<FournitureAd> newlist = shoppingCart.getFournitureAds();
			for (FournitureAd ad : shoppingCart.getFournitureAds())
			{ 
				for(ShoppingCart sh : list)
				{	log.info("list"+sh.getFournitureAds().toString());
					for(FournitureAd f : sh.getFournitureAds())
					if(f.getFaID()==ad.getFaID())
						
						newlist.remove(ad);
										
				}
							
			}
					
			shoppingCart.setFournitureAds(newlist);
			for(FournitureAd d : newlist){log.info("newlist"+d.toString());}
			if(!shoppingCart.getFournitureAds().isEmpty())
				{shoppingCartRepository.save(shoppingCart);}
			else{log.info("list vide");}
		} catch (Exception e) {
			if (e.getClass().equals(SQLIntegrityConstraintViolationException.class))
				log.error("fournitureAd exists in a shoppingcart");
			

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
