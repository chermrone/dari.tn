package tn.dari.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.SubscriptionOrdred;
import tn.dari.spring.repository.RoleRepository;
import tn.dari.spring.service.SubscriptionOrderService;
import tn.dari.spring.service.UserService;

@RestController
@RequestMapping("/dari/subscriptionorder")
public class SubscriptionOrderController {
	@Autowired
	SubscriptionOrderService sos;
	
	@Autowired
	UserService userservice;

	@Autowired
	RoleRepository rr;
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<List<SubscriptionOrdred>> getall(){
		List<SubscriptionOrdred> allsos=sos.GetAll();
		if(allsos.isEmpty()){
			return new ResponseEntity<List<SubscriptionOrdred>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<SubscriptionOrdred>>(allsos,HttpStatus.FOUND);
	}
	
	@GetMapping("/find/{id}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<SubscriptionOrdred> GetById(@PathVariable Long id){
		SubscriptionOrdred s=sos.GetSubscriptionorder(id);
		return new ResponseEntity<SubscriptionOrdred>(s,HttpStatus.OK);
	}
	
	@GetMapping("/getbyuser/{iduser}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<List<SubscriptionOrdred>> GetByUser(@PathVariable Long iduser){
		List<SubscriptionOrdred> usersubsord= sos.GetByUser(iduser);
		if(usersubsord!=null){
			return new ResponseEntity<List<SubscriptionOrdred>>(usersubsord, HttpStatus.FOUND);
		}
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/addpremium/{iduser}")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<SubscriptionOrdred> AddPremiumById(@RequestBody SubscriptionOrdred s,@PathVariable Long iduser){
		return new ResponseEntity<>(sos.AddPremiumSubscriptionorder(s, iduser), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	public ResponseEntity<SubscriptionOrdred> AddSubscriptionOrder(@RequestBody SubscriptionOrdred s){
		return new ResponseEntity<>(sos.AddSubscriptionorder(s), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubscriptionOrdred> UpdateById(@RequestBody SubscriptionOrdred s){
		sos.UpdateSubscriptionorder(s);
		return new ResponseEntity<SubscriptionOrdred>(s,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubscriptionOrdred> DeleteById(@PathVariable Long id){
		sos.deleteSubscriptionOrder(id);
		SubscriptionOrdred s=sos.GetSubscriptionorder(id);
		if(s!=null){
			return new ResponseEntity<SubscriptionOrdred>(HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<SubscriptionOrdred>(HttpStatus.OK);
	}
}
