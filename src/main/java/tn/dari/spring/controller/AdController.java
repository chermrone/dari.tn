package tn.dari.spring.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import tn.dari.spring.service.UIuser;

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
	@GetMapping("/adowned")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")

	public ResponseEntity<List<Ad>> getAdsConnected() {
		
		return new ResponseEntity<List<Ad>>(Adserv.GetAdsOwned(), HttpStatus.OK);
	}
	

	@PostMapping("/add/ad")
	//@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public ResponseEntity<Ad> saveAd(@RequestBody Ad ad) {
		System.out.println("hello");
		Ad AdOne = Adserv.save(ad);
		return new ResponseEntity<Ad>(AdOne, HttpStatus.CREATED);
	}
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")

	@PutMapping("/update/ad")

	public ResponseEntity<Ad> updateAd(@RequestBody Ad ad) {
		if (Adserv.getById(ad.getAdId()) != null) {
			Ad addd = Adserv.save(ad);
			return new ResponseEntity<Ad>(addd, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")
	@DeleteMapping("/delete/{id}")
	public void deleteEmployee(@PathVariable("id") Long id) {
		Adserv.Delete(id);
	}

	@GetMapping("/buyedAdByRegion/{city}")
	public ResponseEntity<String> GetbBuyedHousesByCity(@PathVariable("city") String city) {
		if (Adserv.getBuyedHousesByCity(city) > 0) {
			return new ResponseEntity<>("number of houses: " + Adserv.getBuyedHousesByCity(city), HttpStatus.FOUND);
		}
		return new ResponseEntity<>("No houses found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/buyedAdByRegionandMaxPrice/{city}/{price}")
	public ResponseEntity<String> GetBuyedHousesByCityAndMaxPrice(@PathVariable("city") String city,
			@PathVariable("price") double price) {
		if (Adserv.getBuyedHousesByCityAndMaxprice(city, price) > 0) {
			return new ResponseEntity<>("number of houses: " + Adserv.getBuyedHousesByCityAndMaxprice(city, price),
					HttpStatus.FOUND);
		} else
			return new ResponseEntity<>("No houses found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/buyedAdByRegionandMinPrice/{city}/{price}")
	public ResponseEntity<String> GetBuyedHousesByCityAndMinPrice(@PathVariable("city") String city,
			@PathVariable("price") double price) {
		if (Adserv.getBuyedHousesByCityAndMinprice(city, price) > 0) {
			return new ResponseEntity<>("number of houses: " + Adserv.getBuyedHousesByCityAndMinprice(city, price),
					HttpStatus.FOUND);
		} else
			return new ResponseEntity<>("No houses found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("buyedAdInPeriod/{city}/{period}")
	public ResponseEntity<String> GetBuyedHousesByCityInPeriodOfTime(@PathVariable("city") String city,
			@PathVariable("period") int period) {
		if (Adserv.getBuyedHousesByCityInPeriod(city, period) > 0) {
			return new ResponseEntity<>("number of buyed houses in less then " + period + " days is:"
					+ Adserv.getBuyedHousesByCityInPeriod(city, period), HttpStatus.OK);
		} else
			return new ResponseEntity<>("No houses found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("topfiveregionsbuy")
	public ResponseEntity<String> GetTopFiveRegionBuy() { 
		List<String> topcities = Adserv.topfivecities();
		return new ResponseEntity<String>(topcities.toString(), HttpStatus.OK);

	}

	@GetMapping("GetRegionsordredbybuyingasc")
	public ResponseEntity<String> GetRegionsordredbybuyingasc() {
		List<String> topcities = Adserv.ordercitiesByBuyingdesc();
		return new ResponseEntity<String>(topcities.toString(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")
	@PostMapping("/change/buyed/{id}")
	public ResponseEntity<Ad> BuyedAd(@PathVariable("id") long id) throws Exception {
		Ad AdOne = Adserv.BuyedHouse(id);
System.out.println("enter+"+ AdOne);
		return new ResponseEntity<Ad>(AdOne, HttpStatus.OK);
	}
	@PostMapping("EstimatedPrice")
	public ResponseEntity<String> EstimatedPrice(@RequestBody Ad ad){
		System.out.println(ad.getBuilda());
			return new ResponseEntity<>("Estimated house: " + Adserv.EstimatedHouse(ad), HttpStatus.FOUND);
	}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
}
