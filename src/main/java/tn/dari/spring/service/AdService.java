package tn.dari.spring.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tn.dari.spring.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@Override
	public Ad save(Ad ad) {
	//System.out.println("that user ad   "+ad.getUs());
	User user=ad.getUs();	System.out.println("that user instanciation   "+user);
	Set<Role> strRoles = user.getRoles();
	Role Seller = rolerepository.findByName(Usertype.SELLER).get();
	System.out.println(rolerepository.findByName(Usertype.SELLER).get());
	strRoles.add(Seller);
	user.setRoles(strRoles);
	userrep.save(user);
	System.out.println("that user ad after add seller  "+user);

return adrepository.save(ad);
	}

	@Override
	public Ad modify(long id) {
		Ad add = adrepository.findById(id).get();

		return adrepository.save(add);
	}

	@Override
	public String Delete(long id) {
		adrepository.deleteById(id);
		return "deleted successfully";
	}

	@Override
	public List<Ad> getAll() {
		return adrepository.findAll();
	}

	@Override
	public Ad getById(long id) {
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
		// TODO Auto-generated method stub
		return adrepository.retrieveSellsAdsByCityMaxPrice(city, price);
	}

	@Override
	public float getBuyedHousesByCityInPeriod(String city, int period) {
		// TODO Auto-generated method stub
		return adrepository.retrieveSellsAdsInPeriod(city, period);
	}

	@Override
	public List<String> ordercitiesByBuyingdesc() {
		List<Ad> ads = adrepository.findAll();
		for (int i = 1; i < ads.size(); i++) {
			if (getBuyedHousesByCity(ads.get(i - 1).getCity()) < getBuyedHousesByCity(ads.get(i).getCity())) {
				Ad aux = ads.get(i);
				ads.set(i, ads.get(i - 1));
				ads.set(i - 1, aux);
			}
		}
		List<String> topcities = new ArrayList<>();
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
		for (int i = 1; i < ads.size(); i++) {
			if (getBuyedHousesByCity(ads.get(i - 1).getCity()) < getBuyedHousesByCity(ads.get(i).getCity())) {
				Ad aux = ads.get(i);
				ads.set(i, ads.get(i - 1));
				ads.set(i - 1, aux);
			}
		}
		List<String> topcities = new ArrayList<>();
		topcities.add(ads.get(0).getCity());
		int k = 0;
		for (int j = 1; j < ads.size(); j++) {
			if (!ads.get(j).getCity().equals(ads.get(j - 1).getCity())) {
				topcities.add(ads.get(j).getCity());
				k++;
				if (k == 4) {
					return topcities;
				}
			}
		}
		return topcities;
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
		double RentEstimatePrice=ad.getNumbreOfRooms();System.out.println(RentEstimatePrice);
		double SellEstimatePrice = ad.getArea();
		String[] NorthE = { "bizerte", "tunis", "ariana", "manouba", "ben arous", "nabeul" };
		String[] NorthW = { "beja", "jandouba", "kef", "siliana", "zaghouan" };
		String[] MiddleE = { "mistir", "sousse", "mahdia" };
		String[] MiddleW = { "karawen", "sidi bouzid", "gasrin" };

		String[] SouthE = { "sfax", "gabes", "mednine", "jandouba" };
		String[] SouthW = { "tozeur", "gafsa", "gbelli", "tataouine" };
		
		
		///Case it is a selling house
		///////////////// Case it is a terrain
		if(ad.getTypead().equals(Typead.SELL))
		{if (ad.getType().equals(TypeBatiment.ground)) {
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

		List<String> TopFive=topfivecities();
			for (String city : NorthE) {
				if (city.equals(ad.getCity().toLowerCase())&&TopFive.contains(city)) {
					SellEstimatePrice *= 3500;
				}
				else if(city.equals(ad.getCity().toLowerCase()))
					SellEstimatePrice*=2000;

			}

			for (String city : NorthW) {
				if (city.equals(ad.getCity().toLowerCase())) {
					SellEstimatePrice *= 2000;
				}
			}

			for (String city : MiddleE) {
				if (city.equals(ad.getCity().toLowerCase())&&TopFive.contains(city)) {
					SellEstimatePrice *= 3000;}
					else if (city.equals(ad.getCity().toLowerCase()))
						SellEstimatePrice*=2000;
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
			SellEstimatePrice+=SellEstimatePrice*0.10*ad.getNumbreOfRooms();

		}
		////////////////////: case house 
		if (ad.getType().equals(TypeBatiment.house)) {
			System.out.println("house");	
			System.out.println(ad.getArea()-ad.getBuilda());
		List<String> TopFive=topfivecities();
		SellEstimatePrice=0;
			for (String city : NorthE) {
				if (city.equals(ad.getCity().toLowerCase())&&TopFive.contains(city)) {
					SellEstimatePrice = 3500*(ad.getBuilda())+1000*(ad.getArea()-ad.getBuilda());
					System.out.println(ad.getBuilda());
					System.out.println(ad.getArea()-ad.getBuilda());
				}
				else if(city.equals(ad.getCity().toLowerCase()))
					SellEstimatePrice = 2000*ad.getBuilda()+1000*(ad.getArea()-ad.getBuilda());
			}

			for (String city : NorthW) {
				if (city.equals(ad.getCity().toLowerCase())) {
					SellEstimatePrice = 2000*ad.getBuilda()+300*(ad.getArea()-ad.getBuilda());				}
			}

			for (String city : MiddleE) {
				if (city.equals(ad.getCity().toLowerCase())&&TopFive.contains(city)) {
					SellEstimatePrice = 3000*ad.getBuilda()+1000*(ad.getArea()-ad.getBuilda());					}
					else if (city.equals(ad.getCity().toLowerCase()))
						SellEstimatePrice = 2000*ad.getBuilda()+1000*(ad.getArea()-ad.getBuilda());				}

			for (String city : MiddleW) {
				if (city.equals(ad.getCity().toLowerCase())) {
					SellEstimatePrice = 1500*ad.getBuilda()+200*(ad.getArea()-ad.getBuilda());				}
			}
			for (String city : SouthE) {
				if (city.equals(ad.getCity().toLowerCase())) {
					SellEstimatePrice = 1200*ad.getBuilda()+500*(ad.getArea()-ad.getBuilda());				}
			}

			for (String city : SouthW) {
				if (city.equals(ad.getCity().toLowerCase())) {
					SellEstimatePrice = 1000*ad.getBuilda()+100*(ad.getArea()-ad.getBuilda());				}

			}
			
			SellEstimatePrice+=SellEstimatePrice*0.10*ad.getNumbreOfRooms();

		}
		return SellEstimatePrice;
	}	////////////////case a rent house
	if(ad.getTypead().equals(Typead.RENT))
	{System.out.println(RentEstimatePrice);
	List<String> TopFive=topfivecities();
	if((int)RentEstimatePrice==0) RentEstimatePrice=1;
	else RentEstimatePrice++;
		for (String city : NorthE) {
			if (city.equals(ad.getCity().toLowerCase())&&TopFive.contains(city)) {
				if (RentEstimatePrice==1)RentEstimatePrice *= 400;
				else RentEstimatePrice *=290;
			}
			else if(city.equals(ad.getCity().toLowerCase()))
				{
			if (RentEstimatePrice==1)RentEstimatePrice *= 350;
			else RentEstimatePrice *=230;}
		}

		for (String city : NorthW) {
			if (city.equals(ad.getCity().toLowerCase())) {
				
				if (RentEstimatePrice==1)RentEstimatePrice *= 250;
				else RentEstimatePrice *=150;
			}
		}

		for (String city : MiddleE) {
			if (city.equals(ad.getCity().toLowerCase())&&TopFive.contains(city)) {

				if (RentEstimatePrice==1)RentEstimatePrice *= 380;
				else RentEstimatePrice *=270;
				
				}
				else if (city.equals(ad.getCity().toLowerCase()))
					{
					if (RentEstimatePrice==1)RentEstimatePrice *= 300;
					else RentEstimatePrice *=200;
					}
					
			}

		for (String city : MiddleW) {
			if (city.equals(ad.getCity().toLowerCase())) {
				if (RentEstimatePrice==1)RentEstimatePrice *= 250;
				else RentEstimatePrice *=130;

			}
		}
		for (String city : SouthE) {
			if (city.equals(ad.getCity().toLowerCase())) {
				if (RentEstimatePrice==1)RentEstimatePrice *= 150;
				else RentEstimatePrice *=100;
				
			}
		}

		for (String city : SouthW) {
			if (city.equals(ad.getCity().toLowerCase())) {
				if (RentEstimatePrice==1)RentEstimatePrice *= 150;
				else RentEstimatePrice *=50;
				
			}

		}
		return RentEstimatePrice;

		
	}
	return 0;
	}

	
	
	
	@Override
	public List<Ad> GetAdsOwned() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String userAuthenticated=auth.getName();
	System.out.println(userAuthenticated);
	User userAd=new User();
	userAd=userserv.GetUserByUserName(userAuthenticated);
	System.out.println(userAd);
	Set<Ad>ads=userAd.getAds();
	System.out.println(ads);
	   List<Ad> AdList = new ArrayList<>(ads);

	return AdList;
	}
}