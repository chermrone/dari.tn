package tn.dari.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.*;

import tn.dari.spring.service.UIadService;

@RestController
@CrossOrigin("*")
@RequestMapping("/dari/ads")
public class AdController {
@Autowired UIadService Adserv;
	@GetMapping("/all")
	public ResponseEntity<List<Ad>>getAllSubscriptions(){
		System.out.println("reception de la requete");
		List<Ad> ads = Adserv.getAll();
		return new ResponseEntity<List<Ad>>(ads, HttpStatus.OK);
	}

	@GetMapping("/ad/{id}")
	public ResponseEntity<Ad> get(@PathVariable("id") Long id){
		Ad ad = Adserv.getById(id);
		return new ResponseEntity<Ad>(ad, HttpStatus.OK);
	}

	@PostMapping("/add/ad")
	public ResponseEntity<Ad>save(@RequestBody Ad ad){
		List<Ad> ads = Adserv.getAll();
		for (Ad announce : ads) {
			if( ad.getAdId().equals(announce.getAdId())) {
				return new ResponseEntity<Ad>(HttpStatus.NOT_ACCEPTABLE);
			}
			
		}
		Ad AdOne = Adserv.modify(ad.getAdId());
		return new ResponseEntity<Ad>(AdOne, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/ad/{id}")
	public ResponseEntity<Ad>update(@PathVariable("id") Long id){
		Ad ad=Adserv.getById(id);
		return new ResponseEntity<Ad>(ad, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/ad/{id}")
	public ResponseEntity<String>delete(@PathVariable("id") Long id){
		Adserv.Delete(id);
		if(Adserv.getById(id).getAdId()== id)
		return new ResponseEntity<String>("Ad deleted", HttpStatus.OK);
		else return new ResponseEntity<String>("Ad not fpund", HttpStatus.CONFLICT);
	}
}
