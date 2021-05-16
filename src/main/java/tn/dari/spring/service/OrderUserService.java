package tn.dari.spring.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import tn.dari.spring.controller.utils.DateUtils;
import tn.dari.spring.dto.DailyProfit;
import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.entity.OrderUser;
import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.entity.User;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.OrderUserRepository;

@Service
public class OrderUserService implements IOrderUserService {

	@Autowired
	OrderUserRepository orderUserRepository;
	@Autowired
	UserService userService;
	
	
	private static final Logger log = LoggerFactory.getLogger(OrderUserService.class);

	@Value("${PROFIT_MARGIN}")
	private Integer PROFIT_MARGIN;
	

	@Override
	public List<OrderUser> getAllOrder() {

		return orderUserRepository.findAll();

	}

	@Override
	public OrderUser getOrderById(Long ID) throws ResourceNotFoundException {

		OrderUser order = orderUserRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		return order;
	}

	@Override
	public OrderUser postOrder(OrderUser order) {
		Date date = new Date();
		order.setDateCreated(date);
		List<User> listUser = userService.GetAllUsers();
		ShoppingCart shoppingCart = order.getShoppingCart();
		for (User u : listUser) {
			if (u.getShoppingCart().contains(order.getShoppingCart())) {
				shoppingCart.setUs(u);
				break;
			}
		}
		order.setShoppingCart(shoppingCart);
		List<OrderUser> list = orderUserRepository.findAll();
		for (OrderUser x : list) {
			if (x.getShoppingCart().getShoppingCartId() == order.getShoppingCart().getShoppingCartId()) {
				return null;
			}
		}
		orderUserRepository.save(order);
		return order;
	}

	@Override
	public OrderUser putOrder(Long ID, OrderUser order) throws ResourceNotFoundException {
		if (order.getOrderId() == ID) {
			OrderUser order1 = orderUserRepository.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));

			Date date = new Date();
			order.setDateCreated(date);
			System.out.println("Order:" + order);
			orderUserRepository.save(order);
		}
		return order;
	}

	@Override
	public Map<String, Boolean> deleteOrder(Long ID) throws ResourceNotFoundException {
		OrderUser order = orderUserRepository.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + ID));
		orderUserRepository.delete(order);
		Map<String, Boolean> response = new HashMap<>();
		response.put("FournitureAd deleted : ", Boolean.TRUE);
		return response;
	}

	public Float getTotalProfit(Date dateDebut, Date dateFin) {
		List<OrderUser> list = orderUserRepository.findAll();
		Float totalProfit = 0f;
		for (OrderUser o : list){
			log.info("date compare:"+(o.getDateCreated().compareTo(dateDebut)>=0&&o.getDateCreated().compareTo(dateFin)<=0&&o.isStatusOrd()));
			log.info("list size:"+o.getShoppingCart().getFournitureAds().size());
			if((o.getDateCreated().compareTo(dateDebut)>=0)&&(o.getDateCreated().compareTo(dateFin)<=0)&&o.isStatusOrd()){
				log.info("inside");
				for(FournitureAd s : o.getShoppingCart().getFournitureAds()){
					log.info("FA:"+s);
						totalProfit+=(s.getPrice() * PROFIT_MARGIN / 100);
			}
			}	
		}
		
		return totalProfit;
	}
	
	public List<DailyProfit> getDailyProfit(LocalDate dateDebut, LocalDate dateFin) {
		log.info("dateDebut"+dateDebut);
		log.info("dateFin"+dateFin);
		List<Date> listDates = DateUtils.getDatesBetweenUsingJava8(dateDebut, dateFin);
		listDates.stream().forEach(o -> log.info("date"+o));
		
		 List<DailyProfit>  dailyProfit= new ArrayList<>();
		
		for(Date D : listDates){
			DailyProfit O =  new DailyProfit();
			O.setDate(D);
			O.setProfit(getTotalProfit(D,D));
			dailyProfit.add(O);
			
		}
		/*for(Date D: DailyProfit.keySet()){
			log.info("date"+D);	
		}*/
		
		return dailyProfit;
	}

	@Override
	public OrderUser findByShoppingCartAndStatusOrd(ShoppingCart shoppingCart,boolean statusOrd) throws ResourceNotFoundException {
		OrderUser order = orderUserRepository.findByShoppingCartAndStatusOrd(shoppingCart,statusOrd).orElseThrow(() -> new ResourceNotFoundException("No orders containing the specified shopping Cart"));
		return order;
	}
	

}
