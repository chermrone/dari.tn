package tn.dari.spring.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;

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

import tn.dari.spring.entity.Subscription;
import tn.dari.spring.entity.User;
import tn.dari.spring.service.UISubscriptionService;
import tn.dari.spring.service.UIuser;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/subscriptions")
public class SubscriptionController {
	@Autowired
	UISubscriptionService ss;

	@GetMapping("/all")
	public ResponseEntity<List<Subscription>> getAllSubscriptions() {
		System.out.println("reception de la requete");
		List<Subscription> sub = ss.GetAllSubscriptions();
		return new ResponseEntity<List<Subscription>>(sub, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Subscription> get(@PathVariable("id") Long id) {
		Subscription sub = ss.GetSubscriptionById(id);
		return new ResponseEntity<Subscription>(sub, HttpStatus.OK);
	}

	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<Subscription>save(@RequestBody Subscription subs){
		List<Subscription> allsub = ss.GetAllSubscriptions();
		for (Subscription sub : allsub) {
			if (sub.getSubscriptionId().equals(subs.getSubscriptionId())) {
				return new ResponseEntity<Subscription>(HttpStatus.NOT_ACCEPTABLE);
			}
			
		}
		Subscription subOne = ss.AddSubscription(subs);
		return new ResponseEntity<Subscription>(subOne, HttpStatus.CREATED);
	}

	
	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Subscription> update(@RequestBody Subscription subscription) {
		List<Subscription> allsub=ss.GetAllSubscriptions();
		for (Subscription subscription2 : allsub) {
			if (subscription2.getSubscriptionId()==subscription.getSubscriptionId()){
				Subscription sub = ss.UpdateSubscription(subscription);
				return new ResponseEntity<Subscription>(sub, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Subscription>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	  void deleteEmployee(@PathVariable("id") Long id) throws Exception {
	    ss.DeleteSubscription(id);
	  }
	
	@GetMapping("/ordersubbyusersub/{agemin}/{agemax}")
	public ResponseEntity<List<Subscription>> OrderSubscriptionsByUsersByAge(@PathVariable("agemin") int agemin,@PathVariable("agemax") int agemax ){
		List<Subscription> ordredSubscriptions=ss.OrderSubscriptionsByMaxUserByAge(agemin, agemax);
		if(!ordredSubscriptions.isEmpty()){
			return new ResponseEntity<List<Subscription>>(ordredSubscriptions,HttpStatus.OK);
		}
		return new ResponseEntity<List<Subscription>>(HttpStatus.NO_CONTENT);
		
	}
	
	
}