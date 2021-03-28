package tn.dari.spring.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Role;
import tn.dari.spring.entity.SubscriptionOrdred;
import tn.dari.spring.entity.User;
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

	@Override
	public SubscriptionOrdred AddSubscriptionorder(SubscriptionOrdred s) {
		return sr.save(s);
	}

	@Override
	public SubscriptionOrdred UpdateSubscriptionorder(SubscriptionOrdred s) {
		if(sr.findById(s.getSubscriptionOrderId())!=null){
			return sr.save(s);
		}
		return null;
	}

	@Override
	public void deleteSubscriptionOrder(Long id) {
		if(sr.findById(id)!=null){
			sr.deleteById(id);
		}
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
		if(!roleuser.contains(r)){
			roleuser.add(r);
		}
		userservice.UpdateUser(us);
		s.setUs(us);
		return sr.save(s);
	}

	@Override
	public List<SubscriptionOrdred> GetByEnable(boolean enable) {
		// TODO Auto-generated method stub
		return sr.findByEnable(enable);
	}

}
