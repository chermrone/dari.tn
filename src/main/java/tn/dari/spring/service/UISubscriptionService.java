package tn.dari.spring.service;

import java.util.List;

import tn.dari.spring.entity.Subscription;

public interface UISubscriptionService {
	public Subscription AddSubscription(Subscription s);

	public void DeleteSubscription(Long id) throws Exception;

	public List<Subscription> GetAllSubscriptions();

	public Subscription GetSubscriptionById(Long id);

	public Subscription UpdateSubscription(Subscription sub);
	
<<<<<<< Updated upstream
	List<Subscription> GetSubscriptionByTitle(String title);
	
	List<Subscription> GetSubscritionByPrice(double price);
	
	List<Subscription> GetSubscriptionByPriceAndTitle(String title, Double price);
=======
	public Subscription UpgradeUser(Long iduser, Long subscription_id);
>>>>>>> Stashed changes
}
