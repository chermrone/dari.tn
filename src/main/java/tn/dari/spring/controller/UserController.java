package tn.dari.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	private UIuser user;

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<List<User>> getAllUser() {
		System.out.println("reception de la requete");
		List<User> use = user.GetAllUsers();
		return new ResponseEntity<List<User>>(use, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<User> Getbyid(@PathVariable("id") Long id) {
		User use = user.GetUserById(id);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}

	@GetMapping("/findbyfirst/{firstname}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<User> getUserByFirstName(@PathVariable String firstname) {
		System.out.println("reception de la requete");
		User use = user.GetUserByFirstName(firstname);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}

	@GetMapping("/findbylast/{lastname}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<User> Getbylastname(@PathVariable String lastname) {
		User use = user.GetUserByLastName(lastname);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}

	@GetMapping("/findbyusername/{username}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<User> Getbyusername(@PathVariable String username) {
		User use = user.GetUserByUserName(username);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<User> update(@RequestBody User u) {
		User us = user.UpdateUser(u);
		return new ResponseEntity<User>(us, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	void deleteEmployee(@PathVariable("id") Long id) {
		user.DeleteUser(id);
	}

	@PutMapping("/ban/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	ResponseEntity<String> UserBan(@PathVariable("id") Long id) {
		User us = user.GetUserById(id);
		user.BanUser(id);
		if (!us.isUserState()) {
			return new ResponseEntity<String>("user " + us.getFirstName() + " " + us.getLastName() + " is banned",
					HttpStatus.OK);
		} else
			return new ResponseEntity<String>("error", HttpStatus.NOT_MODIFIED);
	}
	
	
	@PostMapping("/activate_Acount/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public  ResponseEntity<?>  activate_Acount(@PathVariable("id") Long id) {
          
          user.Activate_Acount(id);
           return new ResponseEntity<String>("User account activated",HttpStatus.OK);
	}
	
	@GetMapping("/usersubscribe/{agemin}/{agemax}/{sid}")
	@PreAuthorize("hasAuthority('PREMIUM') or hasAuthority('ADMIN')")
	public ResponseEntity<Long> UserSubscribeByAge(@PathVariable("agemin") int agemin,@PathVariable("agemax") int agemax,@PathVariable("sid") Long sid ){
		Long nbr=user.UserSubscribeAge(agemin, agemax, sid);
		if(nbr==0) return new ResponseEntity<Long>((long) 0,HttpStatus.NO_CONTENT);
		return new ResponseEntity<Long>(nbr, HttpStatus.OK);
		
	}
	
	@PostMapping("/calculTimeConnection/{id}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<?> CalculTimeConnection(@PathVariable("id") Long id ){
		user.CalculTimeConnection(user.GetUserById(id));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
