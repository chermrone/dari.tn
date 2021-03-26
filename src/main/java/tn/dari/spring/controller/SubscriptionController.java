package tn.dari.spring.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;

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
import tn.dari.spring.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/subscriptions")
public class SubscriptionController {
	@Autowired
	UISubscriptionService ss;
	
	@Autowired
	UserService us;

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
<<<<<<< Updated upstream
=======
	//@PreAuthorize("hasRole('BUYER') or hasRole('ADMIN') or hasRole('SELLER') or hasRole('LANDLORD')")
>>>>>>> Stashed changes
	public ResponseEntity<Subscription>save(@RequestBody Subscription subs){
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
	@DeleteMapping("/delete/{id}")
	  void deleteEmployee(@PathVariable("id") Long id) throws Exception {
	    ss.DeleteSubscription(id);
	  }
	
	@PutMapping("/upgrade/{iduser}/{subscriptionid}")
	//@PreAuthorize("hasRole('BUYER') or hasRole('ADMIN') or hasRole('SELLER') or hasRole('LANDLORD')")
	ResponseEntity<String> UpgradeToPremium(@PathVariable Long iduser, @PathVariable Long subscriptionid){
		Subscription s=ss.UpgradeUser(iduser, subscriptionid);
		if(s!=null){
			return new ResponseEntity<String>("user upgrated", HttpStatus.OK);
		}
		return new ResponseEntity<String>("error upgrading", HttpStatus.NOT_MODIFIED);
	}

	@PostMapping("/upgrade")
	ResponseEntity<String> UpgradeToPremium(@RequestBody Long id,@RequestBody double price){
		us.UpgradeToPremium(id, price);
		return new ResponseEntity<String>("upgrad success", HttpStatus.OK);
	}
}
