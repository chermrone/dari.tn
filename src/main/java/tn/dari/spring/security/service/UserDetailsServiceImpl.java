package tn.dari.spring.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.dari.spring.entity.User;
import tn.dari.spring.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("d5al lel loadbyusername");
		System.out.println(username);
		List<User> users = userRepository.findAll();
		for (User us : users) {
			if (us.getUserName().equals(username)){
				System.out.println(us.toString());
				return UserPrinciple.build(us);
				}
		}
		//User user = userRepository.findByUserName(username);/*.orElseThrow(
				//() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
		return null;//
	}
}