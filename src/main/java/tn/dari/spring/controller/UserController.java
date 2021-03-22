package tn.dari.spring.controller;

import java.util.ArrayList;
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

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Claim;
import tn.dari.spring.entity.User;
import tn.dari.spring.service.UIadService;
import tn.dari.spring.service.UIuser;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/Users")
public class UserController {
	@Autowired
	private UIuser user;

	@Autowired
	private UIadService adserv;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUser() {
		System.out.println("reception de la requete");
		List<User> use = user.GetAllUsers();
		return new ResponseEntity<List<User>>(use, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<User> Getbyid(@PathVariable("id") Long id) {
		User use = user.GetUserById(id);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}

	@GetMapping("/findbyfirst/{firstname}")
	public ResponseEntity<User> getUserByFirstName(@PathVariable String firstname) {
		System.out.println("reception de la requete");
		User use = user.GetUserByFirstName(firstname);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}

	@GetMapping("/findbylast/{lastname}")
	public ResponseEntity<User> Getbylastname(@PathVariable String lastname) {
		User use = user.GetUserByLastName(lastname);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}

	@GetMapping("/findbyusername/{username}")
	public ResponseEntity<User> Getbyusername(@PathVariable String username) {
		User use = user.GetUserByUserName(username);
		return new ResponseEntity<User>(use, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestBody User u) {
		User us = user.UpdateUser(u);
		return new ResponseEntity<User>(us, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	void deleteEmployee(@PathVariable("id") Long id) {
		user.DeleteUser(id);
	}

	@PutMapping("/ban/{id}")
	ResponseEntity<String> UserBan(@PathVariable("id") Long id) {
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
			User us = user.GetUserById(id);
			us.setUserState(false);
			if (!us.isUserState()) {
				user.UpdateUser(us);
				return new ResponseEntity<String>("user " + us.getFirstName() + " " + us.getLastName() + " is banned",
						HttpStatus.OK);
			} else
				return new ResponseEntity<String>("error in user ban", HttpStatus.BAD_REQUEST);
		} else
			return new ResponseEntity<String>(HttpStatus.OK);
	}
}
