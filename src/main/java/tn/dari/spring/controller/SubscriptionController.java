package tn.dari.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.Subscription;
import tn.dari.spring.service.SubscriptionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/subscriptions")
public class SubscriptionController {
	@Autowired
	SubscriptionService ss;
	
	@GetMapping("/all")
	public ResponseEntity<List<Subscription>>getAllSubscriptions(){
		System.out.println("reception de la requete");
		List<Subscription> sub = ss.GetAllSubscriptions();
		return new ResponseEntity<List<Subscription>>(sub, HttpStatus.OK);
	}
	
}
