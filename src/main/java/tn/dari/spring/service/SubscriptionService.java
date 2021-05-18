package tn.dari.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Subscription;
import tn.dari.spring.enumeration.SubscriptionType;
import tn.dari.spring.exception.SubscriptionNotFoundException;
import tn.dari.spring.repository.SubscriptionRepository;

@Service
public class SubscriptionService implements UISubscriptionService {

	@Autowired
	SubscriptionRepository sr;
	
	@Autowired
	UserService us;
	
	@Autowired
	SubscriptionOrderService sos;

	@Override
	public Subscription AddSubscription(Subscription s) {
		return sr.save(s);
	}

	@Override
	public void DeleteSubscription(Long id) {
		sr.deleteById(id);
	}

	@Override
	public List<Subscription> GetAllSubscriptions() {
		return sr.findAll();
	}

	@Override
	public Subscription GetSubscriptionById(Long id) {
		return sr.findById(id)
				.orElseThrow(() -> new SubscriptionNotFoundException("subscription by id= " + id + " was not found"));
	}

	@Override
	public Subscription UpdateSubscription(Subscription sub) {
		return sr.save(sub);
	}

	@Override
	public Subscription GetSubscriptionBySubscriptionType(SubscriptionType stype) {
		return sr.findBySubscriptiontype(stype);
	}

	@Override
	public List<Subscription> OrderSubscriptionsByMaxUserByAge(int agemin, int agemax) {
		List<Subscription> alls=GetAllSubscriptions();
		for (int i = 0; i < alls.size(); i++) {
			if(us.UserSubscribeAge(agemin, agemax, alls.get(i).getSubscriptionId())<us.UserSubscribeAge(agemin, agemax, alls.get(i).getSubscriptionId())){
				Subscription aux= alls.get(i);
				alls.set(i, alls.get(i+1));
				alls.set(i+1, aux);
			}
		}
		
		return alls;
	}
	
	



}