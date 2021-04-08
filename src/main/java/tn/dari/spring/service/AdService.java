package tn.dari.spring.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import tn.dari.spring.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Role;
import tn.dari.spring.entity.User;
import tn.dari.spring.enumeration.TypeBatiment;
import tn.dari.spring.enumeration.Typead;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.exception.SubscriptionNotFoundException;
import tn.dari.spring.repository.AdRepository;
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
		if (!user.getRoles().contains(rolerepository.findByName(Usertype.PREMIUM).get())) {
			System.out.println("user isn't premium");
			// search for ad of the after if > 10 out
			Set<Ad> adofUser = user.getAds();
			System.out.println("size" + adofUser.size());
			if (adofUser.size() >= 10)
				return null;
		}
		//// add role SELLER
		Set<Role> strRoles = user.getRoles();
		Role Seller = rolerepository.findByName(Usertype.SELLER).get();
		System.out.println(rolerepository.findByName(Usertype.SELLER).get());
		if(!strRoles.contains(Seller)){
		strRoles.add(Seller);
		user.setRoles(strRoles);
		userrep.save(user);}

		// send mail
		String subject = "Confirmation add announcement";
		if (email.sendMail("tuntechdari.tn@gmail.com", user.getEmail(), subject, "your ad has been successfully added"))
			System.out.println("email has successfully  sent");
		return adrepository.save(ad);
	}

	@Override
	public String Delete(long id) {
		adrepository.deleteById(id);
		return "deleted successfully";
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
		if (email.sendMail("tuntechdari.tn@gmail.com", user.getEmail(), subject,
				"your ad has been successfully modified"))
			System.out.println("email has successfully  sent");
		return adrepository.save(ad);
	}

	@Override
	public List<Ad> getAll() {
		return adrepository.findAll();
	}

	@Override
	public Ad getById(long id) {
		Ad ad=adrepository.findById(id).get();
		 int Visites=ad.getNumbeOfVisites();System.out.println(Visites);
		 Visites++;System.out.println(Visites);
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

	@Override
	public double EstimatedHouse(Ad ad) {
		double RentEstimatePrice = ad.getNumbreOfRooms();
		System.out.println(RentEstimatePrice);
		double SellEstimatePrice = ad.getArea();
		String[] NorthE = { "bizerte", "tunis", "ariana", "manouba", "ben arous", "nabeul" };
		String[] NorthW = { "beja", "jandouba", "kef", "siliana", "zaghouan" };
		String[] MiddleE = { "mistir", "sousse", "mahdia" };
		String[] MiddleW = { "karawen", "sidi bouzid", "gasrin" };

		String[] SouthE = { "sfax", "gabes", "mednine", "jandouba" };
		String[] SouthW = { "tozeur", "gafsa", "gbelli", "tataouine" };

		/// Case it is a selling house
		///////////////// Case it is a terrain
		if (ad.getTypead().equals(Typead.SELL)) {
			if (ad.getType().equals(TypeBatiment.ground)) {
				System.out.println("ground");
				for (String city : NorthE) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 1000;
					}

				}

				for (String city : NorthW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 300;
					}
				}

				for (String city : MiddleE) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 1000;
					}
				}

				for (String city : MiddleW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 200;
					}
				}
				for (String city : SouthE) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 500;
					}
				}

				for (String city : SouthW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 100;
					}

				}

			}

			//////////// :::::case it is a apartment

			if (ad.getType().equals(TypeBatiment.apartment)) {
				System.out.println("apartment");
				SellEstimatePrice = ad.getBuilda();
				System.out.println(SellEstimatePrice);

				List<String> TopFive = topfivecities();
				for (String city : NorthE) {
					if (city.equals(ad.getCity().toLowerCase()) && TopFive.contains(city)) {
						SellEstimatePrice *= 3500;
					} else if (city.equals(ad.getCity().toLowerCase()))
						SellEstimatePrice *= 2000;

				}

				for (String city : NorthW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 2000;
					}
				}

				for (String city : MiddleE) {
					if (city.equals(ad.getCity().toLowerCase()) && TopFive.contains(city)) {
						SellEstimatePrice *= 3000;
					} else if (city.equals(ad.getCity().toLowerCase()))
						SellEstimatePrice *= 2000;
				}

				for (String city : MiddleW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 1500;
					}
				}
				for (String city : SouthE) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 1200;
					}
				}

				for (String city : SouthW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice *= 1000;
					}

				}
				SellEstimatePrice += SellEstimatePrice * 0.10 * ad.getNumbreOfRooms();

			}
			//////////////////// : case house
			if (ad.getType().equals(TypeBatiment.house)) {
				System.out.println("house");
				System.out.println(ad.getArea() - ad.getBuilda());
				List<String> TopFive = topfivecities();
				SellEstimatePrice = 0;
				for (String city : NorthE) {
					if (city.equals(ad.getCity().toLowerCase()) && TopFive.contains(city)) {
						SellEstimatePrice = 3500 * (ad.getBuilda()) + 1000 * (ad.getArea() - ad.getBuilda());
						System.out.println(ad.getBuilda());
						System.out.println(ad.getArea() - ad.getBuilda());
					} else if (city.equals(ad.getCity().toLowerCase()))
						SellEstimatePrice = 2000 * ad.getBuilda() + 1000 * (ad.getArea() - ad.getBuilda());
				}

				for (String city : NorthW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice = 2000 * ad.getBuilda() + 300 * (ad.getArea() - ad.getBuilda());
					}
				}

				for (String city : MiddleE) {
					if (city.equals(ad.getCity().toLowerCase()) && TopFive.contains(city)) {
						SellEstimatePrice = 3000 * ad.getBuilda() + 1000 * (ad.getArea() - ad.getBuilda());
					} else if (city.equals(ad.getCity().toLowerCase()))
						SellEstimatePrice = 2000 * ad.getBuilda() + 1000 * (ad.getArea() - ad.getBuilda());
				}

				for (String city : MiddleW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice = 1500 * ad.getBuilda() + 200 * (ad.getArea() - ad.getBuilda());
					}
				}
				for (String city : SouthE) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice = 1200 * ad.getBuilda() + 500 * (ad.getArea() - ad.getBuilda());
					}
				}

				for (String city : SouthW) {
					if (city.equals(ad.getCity().toLowerCase())) {
						SellEstimatePrice = 1000 * ad.getBuilda() + 100 * (ad.getArea() - ad.getBuilda());
					}

				}

				SellEstimatePrice += SellEstimatePrice * 0.10 * ad.getNumbreOfRooms();

			}
			return SellEstimatePrice;
		} //////////////// case a rent house
		if (ad.getTypead().equals(Typead.RENT)) {
			System.out.println(RentEstimatePrice);
			List<String> TopFive = topfivecities();
			if ((int) RentEstimatePrice == 0)
				RentEstimatePrice = 1;
			else
				RentEstimatePrice++;
			for (String city : NorthE) {
				if (city.equals(ad.getCity().toLowerCase()) && TopFive.contains(city)) {
					if (RentEstimatePrice == 1)
						RentEstimatePrice *= 400;
					else
						RentEstimatePrice *= 290;
				} else if (city.equals(ad.getCity().toLowerCase())) {
					if (RentEstimatePrice == 1)
						RentEstimatePrice *= 350;
					else
						RentEstimatePrice *= 230;
				}
			}

			for (String city : NorthW) {
				if (city.equals(ad.getCity().toLowerCase())) {

					if (RentEstimatePrice == 1)
						RentEstimatePrice *= 250;
					else
						RentEstimatePrice *= 150;
				}
			}

			for (String city : MiddleE) {
				if (city.equals(ad.getCity().toLowerCase()) && TopFive.contains(city)) {

					if (RentEstimatePrice == 1)
						RentEstimatePrice *= 380;
					else
						RentEstimatePrice *= 270;

				} else if (city.equals(ad.getCity().toLowerCase())) {
					if (RentEstimatePrice == 1)
						RentEstimatePrice *= 300;
					else
						RentEstimatePrice *= 200;
				}

			}

			for (String city : MiddleW) {
				if (city.equals(ad.getCity().toLowerCase())) {
					if (RentEstimatePrice == 1)
						RentEstimatePrice *= 250;
					else
						RentEstimatePrice *= 130;

				}
			}
			for (String city : SouthE) {
				if (city.equals(ad.getCity().toLowerCase())) {
					if (RentEstimatePrice == 1)
						RentEstimatePrice *= 150;
					else
						RentEstimatePrice *= 100;

				}
			}

			for (String city : SouthW) {
				if (city.equals(ad.getCity().toLowerCase())) {
					if (RentEstimatePrice == 1)
						RentEstimatePrice *= 150;
					else
						RentEstimatePrice *= 50;

				}

			}
			return RentEstimatePrice;

		}
		return 0;
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
	if(ad.getAdId().equals(id))
		return ad;
}
		return null;
	}

	
	@Override
	public Set<Long> saveFavorite(long id) {
		Ad ad=adrepository.findById(id).get();
long favorite=ad.getAdId();
Set<Long> Favorites=ad.getUs().getFavorite();
		Favorites.add(favorite);
		User user=ad.getUs();
		user.setFavorite(Favorites);
		userrep.save(user);
		return ad.getUs().getFavorite();
	}

	
	@Override
	public int getNumberOfFavoriteAd(long id) {
		return adrepository.retriveNumberOffavoritesForPremium(id);
	}
	
	
	@Override
	public String SituationAd(long id) {
Ad ad=adrepository.findById(id).get();
		User user = ad.getUs();
		// check if he is premium or not : 
	 if (ad.getBuyingDate()==null)
		{//compare if number of visite = 0 feedback
			if(ad.getNumbeOfVisites()==0)
			{ad.setFeedback("you should enter detailled information to your ad and clear image");
		adrepository.save(ad);}
		if(adrepository.retriveNumberOffavoritesForPremium(id)!=0 &&  ad.getNumbeOfVisites()!=0 ){
		//compare date of creation with sysdate if > 7 return estimated price
			Date currentSqlDate = new Date(System.currentTimeMillis());
			long diffInMillies = Math.abs(currentSqlDate.getTime() - ad.getCreationDate().getTime());
			System.out.println(diffInMillies);
			long diff = TimeUnit.MILLISECONDS.toDays(diffInMillies);		
			System.out.println(diff);
			ad.setFeedback("you should reduce the price to attract people");
			adrepository.save(ad);
			if (diff >= 7) {
			return ad.getFeedback()+" your estimated price :  "+EstimatedHouse(ad);}
		}
	}			

		 return ad.getFeedback();

}
	
	@Override
	public  List<Ad>  retriveAdUsingRole(long id){
		Role r=rolerepository.findById(id).get();
				return adrepository.retriveAdDependingOnRole(r.getName()); 
	}
	
	@Override
	public List<Ad> retrieveAdsByBannedUser(Long role, java.util.Date  maxdays,java.util.Date  mindays)
	{
		return adrepository.retrieveUserByBannedAd(role, maxdays, mindays);
	}
	/********************* Statistics*********************************/
	@Override
	@Scheduled(cron="0 46 18 * * *")//execute every day on 18:465)
	public List<String> ordercitiesByBuyingdesc() {
		List<Ad> ads = adrepository.findAll();
		//tri of list ads with buyed houses by city
		for (int i = 1; i < ads.size(); i++) {
			if (getBuyedHousesByCity(ads.get(i - 1).getCity()) < getBuyedHousesByCity(ads.get(i).getCity())) {
				Ad aux = ads.get(i);
				ads.set(i, ads.get(i - 1));
				ads.set(i - 1, aux);
			}
		}
		List<String> topcities = new ArrayList<>();
		//adding (DISTINCT) city in the list of topcities 
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
		//ordering list ads by buyed houses by city
		for (int i = 1; i < ads.size(); i++) {
			if (getBuyedHousesByCity(ads.get(i - 1).getCity()) < getBuyedHousesByCity(ads.get(i).getCity())) {
				Ad aux = ads.get(i);
				ads.set(i, ads.get(i - 1));
				ads.set(i - 1, aux);
			}
		}
		List<String> topcities = new ArrayList<>();
		//adding (DISTINCT) city in the list of topcities 
		topcities.add(ads.get(0).getCity());
		int k = 0;
		for (int j = 1; j < ads.size(); j++) {
			if (!ads.get(j).getCity().equals(ads.get(j - 1).getCity())) {
				topcities.add(ads.get(j).getCity());
				k++;
				if (k == 4) {//this condition(k==4) to stop for if we have 5 cities in our list
					return topcities;
				}
			}
		}
		return topcities;
	}
	}