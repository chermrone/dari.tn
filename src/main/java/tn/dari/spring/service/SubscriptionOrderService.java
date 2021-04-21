package tn.dari.spring.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Role;
import tn.dari.spring.entity.Subscription;
import tn.dari.spring.entity.SubscriptionOrdred;
import tn.dari.spring.entity.User;
import tn.dari.spring.enumeration.SubscriptionType;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.repository.RoleRepository;
import tn.dari.spring.repository.SubscriptionOrderRepository;

@Service
public class SubscriptionOrderService implements UISubscriptionOrderService {

	@Autowired
	SubscriptionOrderRepository sr;

	@Autowired
	UserService userservice;

	@Autowired
	RoleRepository rr;

	@Autowired
	SubscriptionService ss;

	@Override
	public SubscriptionOrdred AddSubscriptionorder(SubscriptionOrdred s,Long id) {
		System.out.println("d5al lel service add");
		// enter the user connected to ad
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userAuthenticated = auth.getName();
		User user = new User();
		user = userservice.GetUserByUserName(userAuthenticated);
		System.out.println(user.toString());
		s.setUs(user);
		
		// enter subscription
		s.setSubscription(ss.GetSubscriptionById(id));
		System.out.println(ss.GetSubscriptionById(id).toString());
		System.out.println(s.toString());
		return sr.save(s);
	}

	@Override
	public SubscriptionOrdred UpdateSubscriptionorder(SubscriptionOrdred s) {
		if (sr.findById(s.getSubscriptionOrderId()) != null) {
			return sr.save(s);
		}
		return null;
	}

	@Override
	public void deleteSubscriptionOrder(Long id) {
		SubscriptionOrdred s = GetSubscriptionorder(id);

		// removing this subscription order from user
		User u = s.getUs();
		Set<SubscriptionOrdred> allso = u.getSubscriptions();
		allso.remove(s);
		u.setSubscriptions(allso);
		userservice.UpdateUser(u);

		// removing this subscription order from subscription
		Subscription subs = s.getSubscription();
		Set<SubscriptionOrdred> allso1 = subs.getSubord();
		allso1.remove(s);
		subs.setSubord(allso1);
		ss.UpdateSubscription(subs);
		sr.delete(GetSubscriptionorder(id));
	}

	@Override
	public List<SubscriptionOrdred> GetAll() {
		return sr.findAll();
	}

	@Override
	public SubscriptionOrdred GetSubscriptionorder(Long id) {
		// TODO Auto-generated method stub
		return sr.findById(id).orElseThrow(() -> new RuntimeException("Fail! -> Cause: Subscription order not found."));
	}

	@Override
	public SubscriptionOrdred AddPremiumSubscriptionorder(SubscriptionOrdred s, Long id) {
		User us = userservice.GetUserById(id);
		Set<Role> roleuser = us.getRoles();
		Role r = rr.findByName(Usertype.PREMIUM)
				.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
		if (!roleuser.contains(r)) {
			roleuser.add(r);
			userservice.UpdateUser(us);
		}
		s.setUs(us);
		s.setSubscription(ss.GetSubscriptionBySubscriptionType(SubscriptionType.premium));
		return sr.save(s);
	}

	@Override
	public List<SubscriptionOrdred> GetByEnable(boolean enable) {
		// TODO Auto-generated method stub
		return sr.findByEnable(enable);
	}

	@Override
	public List<SubscriptionOrdred> GetByUser(Long id) {

		return sr.findByUs(userservice.GetUserById(id));
	}

}
