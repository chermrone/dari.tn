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
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Subscription>get(@PathVariable("id") Long id){
		System.out.println("reception de la requete find by id");
		Subscription sub = ss.GetSubscriptionById(id);
		return new ResponseEntity<Subscription>(sub, HttpStatus.OK);
	}
	
	//accé à cette methode qu'aprés payement
	@PostMapping("/add")
	public ResponseEntity<Subscription>save(@RequestBody Subscription subs){
		System.out.println("reception de la requete post");
		List<Subscription> allsub = ss.GetAllSubscriptions();
		for (Subscription sub : allsub) {
			if (sub.getSubscriptionId().equals(subs.getSubscriptionId())) {
				return new ResponseEntity<Subscription>(HttpStatus.NOT_ACCEPTABLE);
			}
			
		}
		Subscription subOne = ss.UpdateSubscription(subs.getSubscriptionId());
		return new ResponseEntity<Subscription>(subOne, HttpStatus.CREATED);
	}
	
	//l'accée à cette methode doit etre exclusive au admin
	@PutMapping("/update/{id}")
	public ResponseEntity<Subscription>update(@PathVariable("id") Long id){
		System.out.println("reception de la requete put");
		Subscription sub=ss.UpdateSubscription(id);
		return new ResponseEntity<Subscription>(sub, HttpStatus.OK);
	}
	
	//l'accée à cette methode doit etre exclusive au admin
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>delete(@PathVariable("id") Long id){
		ss.DeleteSubscription(id);
		if(ss.GetSubscriptionById(id).getSubscriptionId() == id)
		return new ResponseEntity<String>("Subscription deleted", HttpStatus.OK);
		else return new ResponseEntity<String>("Subscription deleted", HttpStatus.CONFLICT);
	}
	
}
