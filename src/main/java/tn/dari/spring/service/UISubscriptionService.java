package tn.dari.spring.service;

import java.util.List;

import tn.dari.spring.entity.Subscription;
import tn.dari.spring.enumeration.SubscriptionType;

public interface UISubscriptionService {
	public Subscription AddSubscription(Subscription s);

	public void DeleteSubscription(Long id) throws Exception;

	public List<Subscription> GetAllSubscriptions();

	public Subscription GetSubscriptionById(Long id);

	public Subscription UpdateSubscription(Subscription sub);
	
	public Subscription GetSubscriptionBySubscriptionType(SubscriptionType stype);
	
	public List<Subscription> OrderSubscriptionsByMaxUserByAge(int agemin, int agemax);
}
