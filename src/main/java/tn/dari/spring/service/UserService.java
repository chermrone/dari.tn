package tn.dari.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.User;

import tn.dari.spring.exception.UserNotFoundException;
import tn.dari.spring.repository.UserRepository;
@Service
public class UserService implements UIuser {
	@Autowired
UserRepository ur;
	@Override
	public List<User> GetAllUsers() {
		
		return ur.findAll();
	}

	@Override
	public User GetUserById(Long id) {
		
		return ur.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user by id= " + id + " was not found"));
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

}
