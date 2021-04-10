package tn.dari.spring.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import tn.dari.spring.entity.User;

public interface UIuser {
	public List<User> GetAllUsers();

	public User GetUserById(Long id);

	public User GetUserByFirstName(String firstname);

	public User GetUserByLastName(String lastname);

	public User GetUserByUserName(String username);

	public User AddUser(User u);

	public User UpdateUser(User u);

	public void DeleteUser(Long id);
	
	public void BanUser(Long id);
	
	public Long UserSubscribeAge(int agemin,int agemax,Long sid);

	public void logout(Authentication auth);

	public void Activate_Acount(Long Id);
	
	public void CalculTimeConnection(User u);
	
	public List<User> OrderUsersByTimeOfConnection();
}
