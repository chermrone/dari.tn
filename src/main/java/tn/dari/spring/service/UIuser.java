package tn.dari.spring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


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
	
	
	
	public User saveImg(MultipartFile file,String ad) throws Exception;


	public void Deleteimg(String name);
}
