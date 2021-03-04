package tn.dari.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Subscription;
import tn.dari.spring.repository.SubscriptionRepository;
@Service
public class SubscriptionService implements UISubscriptionService {

	@Autowired
	SubscriptionRepository sr;
	
	@Override
	public Subscription AddSubscription(Subscription s) {
		return sr.save(s);
	}

	@Override
	public String DeleteSubscription(Long id) throws Exception {
		sr.deleteById(id);
		if (sr.findById(id).get().getSubscriptionId() == id) throw new Exception("subscription not deleted");
		else return "subscription deleted successfully";
	}

	@Override
	public List<Subscription> GetAllSubscriptions() {
		return sr.findAll();
	}

}
