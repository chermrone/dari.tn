package tn.dari.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.dari.spring.entity.User;
import tn.dari.spring.repository.UserRepository;
import tn.dari.spring.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userServ;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("d5al lel loadbyusername");
		System.out.println(username);
		try {
			User user = userServ.GetUserByUserName(username);
			if(user.isUserState()) return UserPrinciple.build(user);
			else return null;
		} catch (Exception UsernameNotFoundException) {
			new UsernameNotFoundException("User Not Found with -> username or email : " + username);
		}
		return null;
	}
}