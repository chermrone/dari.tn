package tn.dari.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import tn.dari.spring.entity.User;
import tn.dari.spring.service.UIuser;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/Users")
public class UserController {
	@Autowired
	UIuser user;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<User>>getAllUser(){
		System.out.println("reception de la requete");
		List<User> use = user.GetAllUsers();
		return new ResponseEntity<List<User>>(use, HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<User>Getbyid(@PathVariable("id") Long id){
		User use = user.GetUserById(id);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/findbyfirst/{firstname}")
	public ResponseEntity<User>getUserByFirstName(@PathVariable String firstname){
		System.out.println("reception de la requete");
		User use = user.GetUserByFirstName(firstname);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}
	
	@GetMapping("/findbylast/{lastname}")
	public ResponseEntity<User>Getbylastname(@PathVariable String lastname){
		User use = user.GetUserByLastName(lastname);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}
	@GetMapping("/findbyusername/{username}")
	public ResponseEntity<User>Getbyusername(@PathVariable String username){
		User use = user.GetUserByUserName(username);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<User>save(@RequestBody User uses){
		List<User> alluser = user.GetAllUsers();
		for (User use : alluser) {
			if (use.getIdUser().equals(uses.getIdUser())) {
				return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
			}
			
		}
		User useOne = user.AddUser(uses);
		return new ResponseEntity<User>(useOne, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<User>update(@RequestBody User u){
		User us=user.UpdateUser(u);
		return new ResponseEntity<User>(us, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	  void deleteEmployee(@PathVariable("id") Long id) {
	 user.DeleteUser(id);
	  }
	/*
	 * @DeleteMapping("/delete/{id}") public
	 * ResponseEntity<String>delete(@PathVariable("id") Long id){
	 * System.out.println("delete"); user.DeleteUser(id);
	 * if(user.GetUserById(id).getIdUser() ==id) return new
	 * ResponseEntity<String>("User deleted", HttpStatus.OK); else return new
	 * ResponseEntity<String>("not deleted", HttpStatus.NOT_FOUND); }
	 */
}
