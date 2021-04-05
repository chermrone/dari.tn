package tn.dari.spring.security.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.dari.spring.entity.Role;
import tn.dari.spring.entity.Subscription;
import tn.dari.spring.entity.SubscriptionOrdred;
import tn.dari.spring.entity.User;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.repository.RoleRepository;
import tn.dari.spring.repository.UserRepository;
import tn.dari.spring.service.SubscriptionOrderService;
import tn.dari.spring.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userServ;

	@Autowired
	RoleRepository rr;

	@Autowired
	SubscriptionOrderService sos;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("d5al lel loadbyusername");
		System.out.println(username);
		try {
			User user = userServ.GetUserByUserName(username);
			if (user.isUserState()) {
				if (user.getRoles().contains(rr.findByName(Usertype.PREMIUM).get())) {
					Set<SubscriptionOrdred> allsubord = user.getSubscriptions();
					SubscriptionOrdred subordpremium = null;
					List<Subscription> subs = new ArrayList<Subscription>();
					long duration = 0;
					for (SubscriptionOrdred so : allsubord) {
						subs.add(so.getSubscription());
					}
					for (Subscription s : subs) {
						for (SubscriptionOrdred subscription : allsubord) {
							if (subscription.isEnable()) {
								if (subscription.getSubscription().equals(s)) {
									subordpremium = subscription;
									duration = s.getDuration();
									Date sysdate = new Date();
									Date datepay = subordpremium.getPayingDate();
									long diffInMillies = Math.abs(sysdate.getTime() - datepay.getTime());
									long diff = TimeUnit.MILLISECONDS.toDays(diffInMillies);						if (diff >= duration) {
										Set<Role> r = user.getRoles();
										r.remove(rr.findByName(Usertype.PREMIUM).get());
										user.setRoles(r);
										subordpremium.setEnable(false);
										userServ.UpdateUser(user);
										return UserPrinciple.build(user);
									}
								}
							}
						}
					}
					
				}
				return UserPrinciple.build(user);
			} else if(user.getBanNbr()<=3 && TimeUnit.MILLISECONDS.toDays( Math.abs(new Date().getTime() - user.getBanDate().getTime()))>=3){
				userServ.Activate_Acount(user.getIdUser());
				userServ.UpdateUser(user);
				return UserPrinciple.build(user);
			}
			else return null;
		} catch (Exception UsernameNotFoundException) {
			new UsernameNotFoundException("User Not Found with -> username or email : " + username);
		}
		return null;
	}
}