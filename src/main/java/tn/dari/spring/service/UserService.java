package tn.dari.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Claim;
import tn.dari.spring.entity.User;

import tn.dari.spring.exception.UserNotFoundException;
import tn.dari.spring.repository.UserRepository;

@Service
public class UserService implements UIuser {
	@Autowired
	UserRepository ur;
	
	@Autowired
	AdService adserv;

	@Override
	public List<User> GetAllUsers() {

		return ur.findAll();
	}

	@Override
	public User GetUserById(Long id) {
		return ur.findById(id).get();
	}

	@Override
	public User GetUserByFirstName(String firstname) {

		return ur.findByFirstName(firstname);
	}

	@Override
	public User GetUserByLastName(String lastname) {

		return ur.findByLastName(lastname);
	}

	@Override
	public User GetUserByUserName(String username) {

		return ur.findByUserName(username);
	}

	@Override
	public User AddUser(User u) {

		return ur.save(u);
	}

	@Override
	public User UpdateUser(User user) {

		return ur.save(user);
	}

	@Override
	public void DeleteUser(Long id) {
		ur.deleteById(id);

	}
	
	@Override
	public void BanUser(Long id) {
		User us= ur.findById(id).get();
		List<Claim> clmuser = new ArrayList<>();
		us.getAds().forEach(ad -> clmuser.addAll(ad.getClaims()));
		if (clmuser.size() >= 10) {
			us.setUserState(false);
			UpdateUser(us);
		}
	}
	@Override
	public void activate_Acount(Long id) {
		User user = ur.findById(id).get();
		user.setUserState(true);
		ur.save(user);
	}
	
	@Override
	public void logout(Authentication auth) {
		User u = ur.findByUserName(auth.getName());
		u.setConnected(false);
		ur.save(u);
	}

	@Override
	public Long UserSubscribeAge(int agemin, int agemax, Long sid) {
		return ur.UserSubscribeByAge(agemin, agemax, sid);
	}

}
