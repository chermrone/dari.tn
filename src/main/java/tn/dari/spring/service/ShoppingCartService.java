package tn.dari.spring.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.entity.OrderUser;
import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.entity.User;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.ShoppingCartRepository;

@Service
public class ShoppingCartService implements IShoppingCartService {

	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	@Autowired
	FournitureAdService fournitureAdService;
	@Autowired
	OrderUserService orderUserService;

	@Autowired
	UserService userService;

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
	public ShoppingCart getShoppingCartByUsername(String username) throws ResourceNotFoundException {
		User user = userService.GetUserByUserName(username);
		if (user != null) {
			Set<ShoppingCart> shoppingCart = shoppingCartRepository.findByUs(user)
					.orElseThrow(() -> new ResourceNotFoundException("ShoppingCart not found for user :: " + username));
			if (!shoppingCart.isEmpty()) {
				while (shoppingCart.iterator().hasNext()) {
					ShoppingCart sc = shoppingCart.iterator().next();

					int size = orderUserService.getAllOrder().stream()
							.filter(e -> e.getShoppingCart().getShoppingCartId() == sc.getShoppingCartId())
							.collect(Collectors.toList()).size();
					if (size > 0) {
						ShoppingCart newShoppingCart = new ShoppingCart();
						newShoppingCart.setUs(user);
						newShoppingCart.setFournitureAds(new HashSet<>());
						log.info("new shopping cart was created");
						return postShoppingCart(newShoppingCart);
					} else {
						log.info("no orders with the specified shopping cart were found");
						return sc;
					}

				}
			}
		}
		throw new ResourceNotFoundException("ShoppingCart not found for user :: " + username);
	}

	@Override
	public ShoppingCart postShoppingCart(ShoppingCart shoppingCart) {
		try {
			List<ShoppingCart> list = shoppingCartRepository.findAll();
			Set<FournitureAd> newlist = shoppingCart.getFournitureAds();
			for (FournitureAd ad : shoppingCart.getFournitureAds()) {
				for (ShoppingCart sh : list) {
					log.info("list" + sh.getFournitureAds().toString());
					for (FournitureAd f : sh.getFournitureAds())
						if (f.getFaID() == ad.getFaID())
							newlist.remove(ad);
				}

			}

			shoppingCart.setFournitureAds(newlist);
			for (FournitureAd d : newlist) {
				log.info("newlist" + d.toString());
			}
			if (!shoppingCart.getFournitureAds().isEmpty()) {
				shoppingCartRepository.save(shoppingCart);
			} else {
				log.info("list vide");
				shoppingCartRepository.save(shoppingCart);
			}
		} catch (Exception e) {
			if (e.getClass().equals(SQLIntegrityConstraintViolationException.class))
				log.error("fournitureAd exists in a shoppingcart");

		}

		return shoppingCart;
	}

	@Override
	public ShoppingCart putShoppingCart(Long ID, ShoppingCart shoppingCart) throws ResourceNotFoundException {
		log.info("shoppingcart : " + shoppingCart);
		if (shoppingCart.getShoppingCartId() == ID) {
			/*
			 * ShoppingCart shoppingCart1 = shoppingCartRepository.findById(ID)
			 * .orElseThrow(() -> new
			 * ResourceNotFoundException("ShoppingCart Not Founf For this ID :: " + ID));
			 */

			try {
				log.info("in try");
				int notAvailable = 0;
				List<FournitureAd> list = fournitureAdService.getAvailableAd();
				for (FournitureAd x : shoppingCart.getFournitureAds()) {
					if (list.stream().filter(o -> o.getFaID() == x.getFaID()).collect(Collectors.toList())
							.size() == 0) {
						notAvailable++;
					}
				}
				if (notAvailable == shoppingCart.getFournitureAds().size() && notAvailable != 0) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							"All Fourniture Ads in shopping cart are not available");
				}

				log.info("All should be good , going to save the shopping cart");
				shoppingCartRepository.save(shoppingCart);
			} catch (Exception e) {
				if (e.getClass().equals(SQLIntegrityConstraintViolationException.class))
					log.error("fournitureAd exists in a shoppingcart");
				else
					e.printStackTrace();
			}
		} else {
			throw new ResourceNotFoundException("shoppingcart Not Found For this ID :: " + ID);
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
