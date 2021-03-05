package tn.dari.spring.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
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
import tn.dari.spring.entity.User;
import tn.dari.spring.service.UISubscriptionService;
import tn.dari.spring.service.UIuser;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/subscriptions")
public class SubscriptionController {
	@Autowired
	UISubscriptionService ss;
	@Autowired
	UIuser us;
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

	// accé à cette methode qu'aprés payement
	//kinjib el requete mel front lazem njibha bel user fi wostha
	@PostMapping("/add")
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

	// l'accée à cette methode doit etre exclusive au admin
	//kitjib el requete mel front jib el subscription bel id mta3ha kamla
	@PutMapping("/update")
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

	// l'accée à cette methode doit etre exclusive au admin
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) throws Exception {
		ss.DeleteSubscription(id);
		if (ss.GetSubscriptionById(id).getSubscriptionId() == id)
			return new ResponseEntity<String>("Subscription deleted", HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error ", HttpStatus.CONFLICT);
	}

}
