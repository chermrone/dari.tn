package tn.dari.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Subscription;
import tn.dari.spring.exception.SubscriptionNotFoundException;
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

}
