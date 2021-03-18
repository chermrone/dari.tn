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

import tn.dari.spring.entity.*;

import tn.dari.spring.service.UIadService;

@RestController
@CrossOrigin("*")
@RequestMapping("/dari/ads")
public class AdController {
	@Autowired
	UIadService Adserv;

	@GetMapping("/all")
	public ResponseEntity<List<Ad>> getAllAds() {
		
		List<Ad> ads = Adserv.getAll();
		return new ResponseEntity<List<Ad>>(ads, HttpStatus.OK);
	}

	@GetMapping("/ad/{id}")
	public ResponseEntity<Ad> getById(@PathVariable("id") Long id) {
		Ad ad = Adserv.getById(id);
		return new ResponseEntity<Ad>(ad, HttpStatus.OK);
	}

	@PostMapping("/add/ad")
	public ResponseEntity<Ad> saveAd(@RequestBody Ad ad) {
		/*List<Ad> ads = Adserv.getAll();
		System.out.println(ads);
		for (Ad announce : ads) {
			if (ad.getAdId().equals(announce.getAdId())) {
				return new ResponseEntity<Ad>(HttpStatus.NOT_ACCEPTABLE);
			}

		}*/
		Ad AdOne = Adserv.save(ad);
		return new ResponseEntity<Ad>(AdOne, HttpStatus.CREATED);
	}

	// @PutMapping("/update/ad/{id}")
	@PutMapping("/update/ad")

	public ResponseEntity<Ad> updateAd(@RequestBody Ad ad) {
		if (Adserv.getById(ad.getAdId()) != null) {
			Ad addd = Adserv.save(ad);
			return new ResponseEntity<Ad>(addd, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@DeleteMapping("/delete/{id}")
	  public void deleteEmployee(@PathVariable("id") Long id) {
	    Adserv.Delete(id);
	  }
	
	@GetMapping("/buyedAdByRegion/{city}")
	public ResponseEntity<String> GetbBuyedHousesByCity(@PathVariable("city") String city){
		if(Adserv.getBuyedHousesByCity(city)>0){
			return new ResponseEntity<>("number of houses: " + Adserv.getBuyedHousesByCity(city), HttpStatus.FOUND);
		}
		return new ResponseEntity<>("No houses found",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/buyedAdByRegionandMaxPrice/{city}/{price}")
	public ResponseEntity<String> GetBuyedHousesByCityAndMaxPrice(@PathVariable("city") String city,@PathVariable("price") double price){
		if(Adserv.getBuyedHousesByCityAndMaxprice(city, price)>0){
			return new ResponseEntity<>("number of houses: " + Adserv.getBuyedHousesByCityAndMaxprice(city, price), HttpStatus.FOUND);
		}
		else 
			return new ResponseEntity<>("No houses found",HttpStatus.NOT_FOUND);
	}

	@GetMapping("/buyedAdByRegionandMinPrice/{city}/{price}")
	public ResponseEntity<String> GetBuyedHousesByCityAndMinPrice(@PathVariable("city") String city,@PathVariable("price") double price){
		if(Adserv.getBuyedHousesByCityAndMinprice(city, price)>0){
			return new ResponseEntity<>("number of houses: " + Adserv.getBuyedHousesByCityAndMinprice(city, price), HttpStatus.FOUND);
		}
		else 
			return new ResponseEntity<>("No houses found",HttpStatus.NOT_FOUND);
	}
	
	/*
	 * @DeleteMapping("/delete/ad/{id}") public ResponseEntity<String>
	 * delete(@PathVariable("id") Long id) { Adserv.Delete(id); if
	 * (Adserv.getById(id).getAdId() == id) return new
	 * ResponseEntity<String>("Ad deleted", HttpStatus.OK); else return new
	 * ResponseEntity<String>("Ad not found", HttpStatus.NOT_FOUND); }
	 */
}
