package tn.dari.spring.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.*;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.service.UIadService;
import tn.dari.spring.service.UIuser;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
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
	
	@GetMapping("/adowned/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")

	public ResponseEntity<Ad> getAdConnected(@PathVariable("id") Long id) {

		return new ResponseEntity<Ad>(Adserv.GetAdOwned(id), HttpStatus.OK);
	}
	
	

	@PostMapping("/add/ad")
	public ResponseEntity<Ad> saveAd(@RequestBody Ad ad) {
		Ad AdOne = Adserv.save(ad);
		if (AdOne == null)
			return new ResponseEntity<Ad>(HttpStatus.TOO_MANY_REQUESTS);
		return new ResponseEntity<Ad>(AdOne, HttpStatus.CREATED);
	}

	@PostMapping("/add/favorite/{id}")
	public ResponseEntity<Set<Long>> saveFavorite(@PathVariable("id") Long id) {
		Set<Long> favorites = Adserv.saveFavorite(id);
		if (favorites == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<Set<Long>>(favorites, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")

	@PutMapping("/update/ad")

	public ResponseEntity<Ad> updateAd(@RequestBody Ad ad) {
		if (Adserv.getById(ad.getAdId()) != null) {
			Ad addd = Adserv.modify(ad);
			return new ResponseEntity<Ad>(addd, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")
	@DeleteMapping("/delete/{id}")
	public void deleteAd(@PathVariable("id") Long id) {
		System.out.println("deleeeete");
		Adserv.Delete(id);
		System.out.println("deleeeete");
	}

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SELLER')")
	@PostMapping("/change/buyed/{id}")
	public ResponseEntity<Ad> BuyedAd(@PathVariable("id") long id) throws Exception {
		Ad AdOne = Adserv.BuyedHouse(id);
		System.out.println("enter+" + AdOne);
		return new ResponseEntity<Ad>(AdOne, HttpStatus.OK);
	}

	@PostMapping("EstimatedPrice")
	public ResponseEntity<String> EstimatedPrice(@RequestBody Ad ad) {
		System.out.println(ad.getBuilda());
		return new ResponseEntity<>("Estimated house: " + Adserv.EstimatedHouse(ad), HttpStatus.OK);
	}
	
	
	@GetMapping("ad/numfav/{id}")
	public ResponseEntity<Integer> NumberOffavoriteAd(@PathVariable("id") long id)  {
		int numberFav = Adserv.getNumberOfFavoriteAd(id);
		System.out.println("number of favorite ad +" + numberFav);
		return new ResponseEntity<Integer>(numberFav, HttpStatus.OK);
	}
	//ad if premium if the house pass  7 days without being buyed
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('PREMIUM') ")
	@GetMapping("ad/situation/{id}")
	public ResponseEntity<String> SituationAd(@PathVariable("id") long id) {
		String state = Adserv.SituationAd(id);
		if(state !=null)
		return new ResponseEntity<String>(state, HttpStatus.OK);
		else 		return new ResponseEntity<String>(state, HttpStatus.FORBIDDEN);

	}
	@GetMapping("ad/find/{id}")
	public ResponseEntity<List<Ad>> GetAdFromUserByRole(@PathVariable long id)  {
List <Ad> ads=Adserv.retriveAdUsingRole(id);
		return new ResponseEntity<List<Ad>>(ads, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	
	@PostMapping("banned/{role}")
	public ResponseEntity<List<Ad>> GetAdBannedByRoleAndPeriod(@PathVariable Long role,
			@RequestParam(value="fromDate")     @DateTimeFormat(pattern="dd.MM.yyyy") Date fromDate,
			@RequestParam(value="toDate")     @DateTimeFormat(pattern="dd.MM.yyyy") Date toDate)  {
List <Ad> ads=Adserv.retrieveAdsByBannedUser(role, toDate, fromDate);
System.out.println("number of ad +"+ads.size());
		return new ResponseEntity<List<Ad>>(ads, HttpStatus.OK);
	}
	@PostMapping("ad/estimateDuration")
	public ResponseEntity<Integer> EstimatedPeriodSellHouse(@RequestBody Ad ad)  {
int estimatePeriod=Adserv.EstimatedPeriodSellHouse(ad);
		return new ResponseEntity<>(estimatePeriod, HttpStatus.OK);
			}
	@GetMapping("ad/lastad")
	public ResponseEntity<Ad> getLastAd() {
		Ad ad = Adserv.GetAdLast();System.out.println("last ad"+ad);
		return new ResponseEntity<Ad>(ad, HttpStatus.OK);
	}

	
			/****************Ad statistics**************************/
	
	@GetMapping("/buyedAdByRegion/{city}")
	@PreAuthorize("hasAuthority('PREMIUM') or hasAuthority('ADMIN')")
	public ResponseEntity<?> GetbBuyedHousesByCity(@PathVariable("city") String city) {
		
			return new ResponseEntity<>(Adserv.getBuyedHousesByCity(city), HttpStatus.OK);
	}

	@GetMapping("/buyedAdByRegionandMaxPrice/{city}/{price}")
	@PreAuthorize("hasAuthority('PREMIUM') or hasAuthority('ADMIN')")
	public ResponseEntity<?> GetBuyedHousesByCityAndMaxPrice(@PathVariable("city") String city,
			@PathVariable("price") double price) {
			return new ResponseEntity<>(Adserv.getBuyedHousesByCityAndMaxprice(city, price),
					HttpStatus.OK);

	}

	@GetMapping("/buyedAdByRegionandMinPrice/{city}/{price}")
	@PreAuthorize("hasAuthority('PREMIUM') or hasAuthority('ADMIN')")
	public ResponseEntity<?> GetBuyedHousesByCityAndMinPrice(@PathVariable("city") String city,
			@PathVariable("price") double price) {
			return new ResponseEntity<>(Adserv.getBuyedHousesByCityAndMinprice(city, price),
					HttpStatus.OK);
		
	}

	@GetMapping("buyedAdInPeriod/{city}/{period}")
	@PreAuthorize("hasAuthority('PREMIUM') or hasAuthority('ADMIN')")
	public ResponseEntity<?> GetBuyedHousesByCityInPeriodOfTime(@PathVariable("city") String city,
			@PathVariable("period") int period) {
			return new ResponseEntity<>(Adserv.getBuyedHousesByCityInPeriod(city, period), HttpStatus.OK);
	}

	@GetMapping("topfiveregionsbuy")
	@PreAuthorize("hasAuthority('PREMIUM') or hasAuthority('ADMIN')")
	public ResponseEntity<String> GetTopFiveRegionBuy() {
		List<String> topcities = Adserv.topfivecities();
		return new ResponseEntity<String>(topcities.toString(), HttpStatus.OK);

	}

	@GetMapping("GetRegionsordredbybuyingasc")
	@PreAuthorize("hasAuthority('PREMIUM') or hasAuthority('ADMIN')")
	public ResponseEntity<List<String>> GetRegionsordredbybuyingasc() {
		List<String> topcities = Adserv.ordercitiesByBuyingdesc();
		return new ResponseEntity<List<String>>(topcities, HttpStatus.OK);
	}
	
	

}
