package tn.dari.spring.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import tn.dari.spring.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.City;
import tn.dari.spring.entity.Role;
import tn.dari.spring.entity.User;
import tn.dari.spring.enumeration.TypeBatiment;
import tn.dari.spring.enumeration.Typead;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.exception.SubscriptionNotFoundException;
import tn.dari.spring.repository.AdRepository;
import tn.dari.spring.repository.Citiesrepository;
import tn.dari.spring.repository.RoleRepository;
import tn.dari.spring.repository.UserRepository;

@Service
public class AdService implements UIadService {

	@Autowired
	AdRepository adrepository;
	@Autowired
	UserRepository userrep;
	@Autowired
	RoleRepository rolerepository;

	@Autowired
	UIuser userserv;
	@Autowired
	EmailService email;
	@Autowired
	Citiesrepository citiesrep;

	@Override
	public Ad save(Ad ad) {

		// enter the user connected to ad
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userAuthenticated = auth.getName();
		System.out.println(userAuthenticated);
		User userAd = new User();
		userAd = userserv.GetUserByUserName(userAuthenticated);
		ad.setUs(userAd);
		User user = ad.getUs();
		// check if he is premium or not :max 10 post ad if he is not
		/*if (!user.getRoles().contains(rolerepository.findByName(Usertype.PREMIUM).get())
				|| user.getRoles().contains(rolerepository.findByName(Usertype.ADMIN).get())==false) {
			System.out.println("user isn't premium");
			// search for ad of the after if > 10 out
			Set<Ad> adofUser = user.getAds();
			System.out.println("size" + adofUser.size());
			if (adofUser.size() >= 10)
				return null;
		}*/
		//// add role SELLER
		Set<Role> strRoles = user.getRoles();
		Role Seller = rolerepository.findByName(Usertype.SELLER).get();
		System.out.println(rolerepository.findByName(Usertype.SELLER).get());
		if (!strRoles.contains(Seller)) {
			strRoles.add(Seller);
			user.setRoles(strRoles);
			userrep.save(user);
		}

		// send mail
	/*	String subject = "Confirmation add announcement";
		if (email.sendMail("tuntechdari.tn@gmail.com", user.getEmail(), subject, "your ad has been successfully added"))
	*/		System.out.println("email has successfully  sent");
		return adrepository.save(ad);
	}

	@Override
	public void Delete(long id) {
		System.out.println("delete" + id);
		Ad ad = adrepository.findById(id).get();
		try {
			adrepository.deleteImgAd(ad);
		} catch (Exception e) {
		}
		;
		
		
		try {
			adrepository.deleteClaim(ad);
		} catch (Exception e) {
		}
		try {
			adrepository.deleteReview(ad);
		} catch (Exception e) {
		}
		try {
			adrepository.deleteWishlist(ad);
		} catch (Exception e) {
		}			adrepository.deleteAd(id);

		System.out.println("delete");
	}

	@Override
	public Ad modify(Ad ad) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userAuthenticated = auth.getName();
		System.out.println(userAuthenticated);
		User userAd = new User();
		userAd = userserv.GetUserByUserName(userAuthenticated);
		ad.setUs(userAd);
		User user = ad.getUs();
		System.out.println(user);
		String subject = "Modify announcement";
		/*if (email.sendMail("tuntechdari.tn@gmail.com", user.getEmail(), subject,
				"your ad has been successfully modified"))
			System.out.println("email has successfully  sent");*/
		return adrepository.save(ad);
	}

	@Override
	public List<Ad> getAll() {
		
		return adrepository.findAll();
	}

	@Override
	public Ad getById(long id) {
		Ad ad = adrepository.findById(id).get();
		int Visites = ad.getNumbeOfVisites();
		System.out.println(Visites);
		Visites++;
		System.out.println(Visites);
		ad.setNumbeOfVisites(Visites);
		adrepository.save(ad);
		return adrepository.findById(id)
				.orElseThrow(() -> new AdNotFoundException("Ad by id= " + id + " was not found"));
	}

	@Override
	public float getBuyedHousesByCity(String city) {
		return adrepository.retrieveSellsAdsBycity(city);
	}

	@Override
	public float getBuyedHousesByCityAndMinprice(String city, double price) {
		return adrepository.retrieveSellsAdsByCityMinPrice(city, price);
	}

	@Override
	public float getBuyedHousesByCityAndMaxprice(String city, double price) {
		return adrepository.retrieveSellsAdsByCityMaxPrice(city, price);
	}

	@Override
	public float getBuyedHousesByCityInPeriod(String city, int period) {
		return adrepository.retrieveSellsAdsInPeriod(city, period);
	}

	@Override
	public Ad BuyedHouse(long id) {
		Ad ad = adrepository.findById(id).orElseThrow(() -> new AdNotFoundException("Ad  " + id + " not found"));
		Date currentSqlDate = new Date(System.currentTimeMillis());

		ad.setBuyingDate(currentSqlDate);
		return adrepository.save(ad);
	}

	@Scheduled(cron = "0 31 19 * * *")
	@Override
	public void UpdateTopFiveInTableCity() {
		String[] TopFive ={"sfax","tunis","sousse","bizerte","nabeul","ariana","ben arous","mistir","ariana"};
		adrepository.UpdateCitiesFamous(TopFive);
	}

	/* (non-Javadoc)
	 * @see tn.dari.spring.service.UIadService#EstimatedHouse(tn.dari.spring.entity.Ad)
	 */
	@Override
	public double EstimatedHouse(Ad ad) {
		double RentEstimatePrice = ad.getNumbreOfRooms();
		System.out.println(RentEstimatePrice);
		// connected user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userAuthenticated = auth.getName();
		System.out.println("this user is connected" + userAuthenticated);
		User userAd = new User();
		userAd = userserv.GetUserByUserName(userAuthenticated);
		String[] famous ={"sfax","tunis","sousse","bizerte","nabeul","ariana","ben arous","mistir","ariana"};
		//System.out.println("cities  " +famous.toString());
		boolean verif=false;
		for (String string : famous) {

			if(string.equals(ad.getCity()))
				{verif=true;System.out.println(verif+"hhhhhhhhhhhhh");}
		}
		adrepository.UpdateCitiesFamous(famous);
		double estimateprice = 0;
		/// Case it is a selling house
		/////////////////
		//////////// :::::Case it is a ground::::::::://////////
	if (ad.getType().equals(TypeBatiment.ground))
		{System.out.println("hello entred ground"+ ad.getArea());
				estimateprice = adrepository.RetrievEstimatedPriceGround(ad.getArea(), ad.getType().name(),
						ad.getCity());
				System.out.println(estimateprice);

		}
			if (estimateprice != 0)
				return estimateprice;
		if (ad.getTypead().equals(Typead.SELL)) {
		

			//////////// :::::case it is a apartment::::::::://////////

			if (ad.getType().equals(TypeBatiment.apartment)) {
				System.out.println("it is apartment");
		/*			estimateprice = adrepository.RetrievEstimatedPriceFamousRegionAppartment(ad.getBuilda(),
							ad.getType().name(), ad.getCity(), userAd);
				if (estimateprice != 0)
					return estimateprice;*/

				estimateprice = adrepository.RetrievEstimatedPriceAppartment(ad.getBuilda(),
						ad.getType().name(), ad.getCity());
				if (estimateprice != 0)
					return estimateprice;
			}

			//////////// :::::case it is a house::::::::://////////

			if (ad.getType().equals(TypeBatiment.house)) {

			/*	if (verif)
					estimateprice = adrepository.RetrievEstimatedPriceFamousRegionHouse(ad.getBuilda(), ad.getArea(),
							ad.getType().name(), ad.getCity(), userAd);
				else
*/
					estimateprice = adrepository.RetrievEstimatedPriceHouse(ad.getBuilda(), ad.getArea(),
							ad.getType().name(), ad.getCity(), userAd);

				estimateprice += estimateprice * 0.10 * ad.getNumbreOfRooms();
				return estimateprice;
			}
		}

		//////////////// case a rent house
		if (ad.getTypead().equals(Typead.RENT)) {
			int rooms = ad.getNumbreOfRooms();
			if (rooms == 0)
				rooms = 1;
			else
				rooms++;
			/*if (!verif) {*/
				if (rooms == 1)
					estimateprice += adrepository.RetrievEstimatedRentPriceOnlyRoom(rooms, ad.getCity(),
							userAd);
				if (rooms != 1)
					estimateprice += adrepository.RetrievEstimatedRentPriceMultiRoom(rooms, ad.getCity(),
							userAd);
			}/* else {
				if (rooms == 1)
					estimateprice += adrepository.RetrievEstimatedRentPriceFamousRegionOnlyRoom(rooms, ad.getCity(),
							userAd);
				if (rooms != 1)
					estimateprice += adrepository.RetrievEstimatedRentPriceFamousRegionMultiRoom(rooms, ad.getCity(),
							userAd);
			}*/
			return estimateprice;
	/*	}
		return 0;*/
	}

	@Override
	public Ad GetAdLast(){
		return adrepository.findTopByOrderByAdIdDesc();
	}
	
	@Override
	public List<Ad> GetAdsOwned() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userAuthenticated = auth.getName();
		System.out.println(userAuthenticated);
		User userAd = new User();
		userAd = userserv.GetUserByUserName(userAuthenticated);
		System.out.println(userAd);
		Set<Ad> ads = userAd.getAds();
		System.out.println(ads);
		List<Ad> AdList = new ArrayList<>(ads);

		return AdList;
	}

	@Override
	public Ad GetAdOwned(long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userAuthenticated = auth.getName();
		System.out.println(userAuthenticated);
		User userAd = new User();
		userAd = userserv.GetUserByUserName(userAuthenticated);
		System.out.println(userAd);
		Set<Ad> ads = userAd.getAds();
		for (Ad ad : ads) {
			if (ad.getAdId().equals(id))
				return ad;
		}
		return null;
	}

@Override
public void savFav(long id,String us)
{User userAd = userserv.GetUserByUserName(us);
	Set<Long> Favorites = userAd.getFavorite(); System.out.println(Favorites);
	Favorites.add(id);
	userAd.setFavorite(Favorites);System.out.println( userAd.getFavorite());
	userrep.save(userAd);
	}
	@Override
	public int getNumberOfFavoriteAd(long id) {
		return adrepository.retriveNumberOffavoritesForPremium(id);
	}

	@Override
	public String SituationAd(long id) {
		// to know who is connected (need it after in if for ban must be admin)

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userAuthenticated = auth.getName();
		System.out.println("this user is connected" + userAuthenticated);
		User userAd = new User();
		userAd = userserv.GetUserByUserName(userAuthenticated);

		Ad ad = adrepository.findById(id).get();
		// ad banned + admin
		if (adrepository.CheckAdBanned(id) && adrepository.CheckExistingRoleADMINforConsulterOfAd(userAd)) {
			ad.setFeedback("this ad is banned");
			adrepository.save(ad);
			return ad.getFeedback();
		}
		// when the ad is bannned and the user connected isnt admin
		/*else if (adrepository.CheckExistingRoleADMINforConsulterOfAd(userAd) == false && adrepository.CheckAdBanned(id))
			return null;*/

		if (ad.getBuyingDate() == null) {// compare if number of visite = 0
											// feedback
			System.out.println("ennnnnnnnnnnnnnn");

			if (ad.getNumbeOfVisites() == 0) {
				ad.setFeedback("you should enter detailled information to your ad and clear image");
				adrepository.save(ad);
			}
			if (adrepository.retriveNumberOffavoritesForPremium(id) != 0 || ad.getNumbeOfVisites() != 0
					&& adrepository.CheckAdBanned(id) != true) {
				// compare date of creation with sysdate if > 7 return estimated
				// price
				Date currentSqlDate = new Date(System.currentTimeMillis());
				long diffInMillies = Math.abs(currentSqlDate.getTime() - ad.getCreationDate().getTime());
				System.out.println(diffInMillies);
				long diff = TimeUnit.MILLISECONDS.toDays(diffInMillies);
				System.out.println(diff);
				ad.setFeedback("you should reduce the price to attract people");
				adrepository.save(ad);
				
				if (diff >= 7) {
					return ad.getFeedback() + " your estimated price :  " + EstimatedHouse(ad);
				}
			}

		}
		else 
			{ad.setFeedback("your ad is ok");
		adrepository.save(ad);}
		return ad.getFeedback();

	}

	@Scheduled(cron = "0 43 18 * * *")
	@Override
	public void UpdateEstimatedPeriodSellHouse() {
		List<City> cities = citiesrep.findAll();
		for (City cities2 : cities) {
			adrepository.UpdateEstimatedPeriodSellHouse(cities2.getName());
		}

	}

	@Override
	public int EstimatedPeriodSellHouse(Ad ad) {
		List<City> cities = citiesrep.findAll();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userAuthenticated = auth.getName();
		System.out.println("this user is connected" + userAuthenticated);
		User userAd = new User();
		userAd = userserv.GetUserByUserName(userAuthenticated);
		int period = 0;
		if (ad.getPrice() <= EstimatedHouse(ad)) {
			try {

				period = adrepository.RetrievEstimatedPeriodIdealCities(ad.getCity(), ad.getPrice(), EstimatedHouse(ad),
						userAd);
				return period;
			} catch (Exception e) {
			}
			try {
				period = adrepository.RetrievEstimatedPeriodNonIdealCitiesWinterSeason(ad.getCity(), ad.getPrice(),
						EstimatedHouse(ad), userAd);
				return period;
			} catch (Exception e) {
			}
		}

		else if (ad.getPrice() > EstimatedHouse(ad)) {
			try {
				period = adrepository.RetrievEstimatedPeriodNonIdealCities(ad.getCity(), ad.getPrice(),
						EstimatedHouse(ad), userAd);
				return period;
			} catch (Exception e) {
			}
			try {
				System.out.println("helooo entred");

				period = adrepository.RetrievEstimatedPeriodNonIdealCitiesOverPrice(ad.getCity(), ad.getPrice(),
						EstimatedHouse(ad), userAd);
				period += 30;
				System.out.println("helooo entred +" + period);
				return period;
			} catch (Exception e) {
			}
		}

		System.out.println("helooo end");
		return period;

	}

	@Override
	public List<Ad> retriveAdUsingRole(long id) {
		Role r = rolerepository.findById(id).get();
		return adrepository.retriveAdDependingOnRole(r.getName());
	}

	@Override
	public List<Ad> retrieveAdsByBannedUser(Long role, java.util.Date maxdays, java.util.Date mindays) {
		return adrepository.retrieveAdByBannedUser(role, maxdays, mindays);
	}

	/********************* Statistics *********************************/
	@Override
	@Scheduled(cron = "0 46 18 * * *") // execute every day on 18:465)
	public List<String> ordercitiesByBuyingdesc() {
		List<Ad> ads = adrepository.findAll();
		// tri of list ads with buyed houses by city
		for (int i = 1; i < ads.size(); i++) {
			if (getBuyedHousesByCity(ads.get(i - 1).getCity()) < getBuyedHousesByCity(ads.get(i).getCity())) {
				Ad aux = ads.get(i);
				ads.set(i, ads.get(i - 1));
				ads.set(i - 1, aux);
			}
		}
		List<String> topcities = new ArrayList<>();
		// adding (DISTINCT) city in the list of topcities
		topcities.add(ads.get(0).getCity());
		for (int j = 1; j < ads.size(); j++) {
			if (!ads.get(j).getCity().equals(ads.get(j - 1).getCity())) {
				topcities.add(ads.get(j).getCity());
			}
		}
		return topcities;
	}

	@Override
	public List<String> topfivecities() {
		List<Ad> ads = adrepository.findAll();
		// ordering list ads by buyed houses by city
		for (int i = 1; i < ads.size(); i++) {
			if (getBuyedHousesByCity(ads.get(i - 1).getCity()) < getBuyedHousesByCity(ads.get(i).getCity())) {
				Ad aux = ads.get(i);
				ads.set(i, ads.get(i - 1));
				ads.set(i - 1, aux);
			}
		}
		List<String> topcities = new ArrayList<>();
		// adding (DISTINCT) city in the list of topcities
		topcities.add(ads.get(0).getCity());
		int k = 0;
		for (int j = 1; j < ads.size(); j++) {
			if (!ads.get(j).getCity().equals(ads.get(j - 1).getCity())) {
				topcities.add(ads.get(j).getCity());
				k++;
				if (k == 4) {// this condition(k==4) to stop for if we have 5
								// cities in our list
					return topcities;
				}
			}
		}
		return topcities;
	}

	@Override
	public List<Ad> GetAdsRent() {
	return adrepository.retriveAdRent();
	}

	@Override
	public List<Ad> GetAdsSell() {
		return adrepository.retriveAdSell();

	}

	@Override
	public List<Ad> ReetreivefavOwned() {Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String userAuthenticated = auth.getName();
	System.out.println(userAuthenticated);
	User userAd = new User();List<Ad>adfin = new ArrayList<>();
	userAd = userserv.GetUserByUserName(userAuthenticated);
	List<Long> numAd= adrepository.retrievefavOwned(userAd.getIdUser());
	List<Ad> ads = adrepository.findAll();
	for (Ad ad : ads) {for (long idadFav : numAd) {//System.out.println("eeeeee"+idadFav);
		if(ad.getAdId().equals(idadFav))
			{adfin.add(ad);System.out.println("fin");}
	}
	} 
	return adfin;
	}

	@Override
	public void Deletefav(Long id) {
adrepository.deletefavid(id);		
	}

	@Override
	public List<Ad> getAdByCaracteristic(Typead typeAd, TypeBatiment typebat, double price, int rooms, String city) {
		if(typeAd==null && typebat==TypeBatiment.ground)
return adrepository.RetrieveGroundByPriceAndCityAndNumbreOfRoomsAndTypeadAndType(price,city,rooms,typebat);
		return adrepository.RetrieveByPriceAndCityAndNumbreOfRoomsAndTypeadAndType(price,city,rooms,typeAd,typebat);
	}

	@Override
	public String[] retrieveFavCity(long id) {
		return adrepository.getMostFav(id);
//return adrepository.getCityFav(id);
	}
	@Override
	public List<Ad> MostCheapestAdRENT()
	{return adrepository.RetrieveOrderedRENT();}

	@Override
	public List<Ad> MostCheapestAdSELL()
	{return adrepository.RetrieveOrderedSELL();}
}