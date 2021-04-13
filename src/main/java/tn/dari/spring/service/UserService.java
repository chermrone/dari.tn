package tn.dari.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Claim;
import tn.dari.spring.entity.User;
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
		//return ur.findById(id).orElseThrow(() -> new UserNotFoundException("user by id= " + id + " was not found"));
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

		return ur.findByUserName(username);//.get();
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
		List<Ad> ad = adserv.getAll();
		List<Ad> aduser = new ArrayList<>();
		for (Ad ad2 : ad) {
			if (ad2.getUs().getIdUser() == id) {
				aduser.add(ad2);
			}
		}
		List<Claim> clmuser = new ArrayList<>();
		for (Ad ad3 : aduser) {
			clmuser.addAll(ad3.getClaims());
		}
		if (clmuser.size() >= 10) {
			User us = GetUserById(id);
			us.setUserState(false);
			UpdateUser(us);
		}
	}

}
