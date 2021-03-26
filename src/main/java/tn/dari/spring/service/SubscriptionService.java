package tn.dari.spring.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Role;
import tn.dari.spring.entity.Subscription;
import tn.dari.spring.entity.User;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.exception.SubscriptionNotFoundException;
import tn.dari.spring.repository.RoleRepository;
import tn.dari.spring.repository.SubscriptionRepository;

@Service
public class SubscriptionService implements UISubscriptionService {

	@Autowired
	SubscriptionRepository sr;

	@Autowired
	UserService userservice;

	@Autowired
	RoleRepository rr;

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
<<<<<<< Updated upstream
	public List<Subscription> GetSubscriptionByTitle(String title) {
		// TODO Auto-generated method stub
		return sr.findByTitle(title);
	}

	@Override
	public List<Subscription> GetSubscritionByPrice(double price) {
		// TODO Auto-generated method stub
		return sr.findByPrice(price);
	}

	@Override
	public List<Subscription> GetSubscriptionByPriceAndTitle(String title, Double price) {
		// TODO Auto-generated method stub
		return sr.findByTitleAndPrice(title, price);
=======
	public Subscription UpgradeUser(Long iduser, Long subscription_id) {
		User us = userservice.GetUserById(iduser);
		Set<Role> roleuser = us.getRoles();
		Role r = rr.findByName(Usertype.PREMIUM)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		Subscription s = sr.findById(subscription_id)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: Subcription not find."));
		Set<User> u = s.getUs();//list of users contained in this subscription
		if (!roleuser.contains(r)) {
			roleuser.add(r);
		}
		if (us.getRoles().contains(r)) {
			u.add(us);
			s.setUs(u);
		}
		if(roleuser.contains(r) && s.getUs().contains(us)){
			userservice.UpdateUser(us);
			return UpdateSubscription(s);
		}
		return null;//failed
>>>>>>> Stashed changes
	}

}
