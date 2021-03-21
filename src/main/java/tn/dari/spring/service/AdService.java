package tn.dari.spring.service;

import java.util.ArrayList;
import java.util.List;
import tn.dari.spring.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.exception.SubscriptionNotFoundException;
import tn.dari.spring.repository.AdRepository;

@Service
public class AdService implements UIadService {

	@Autowired
	AdRepository adrepository;

	@Override
	public Ad save(Ad ad) {

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
		List<Ad> ads=adrepository.findAll();
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
		List<Ad> ads=adrepository.findAll();
		for (int i = 1; i < ads.size(); i++) {
			if (getBuyedHousesByCity(ads.get(i - 1).getCity()) < getBuyedHousesByCity(ads.get(i).getCity())) {
				Ad aux = ads.get(i);
				ads.set(i, ads.get(i - 1));
				ads.set(i - 1, aux);
			}
		}
		List<String> topcities = new ArrayList<>();
		topcities.add(ads.get(0).getCity());
		int k=0;
		for (int j = 1; j < ads.size(); j++) {
			if (!ads.get(j).getCity().equals(ads.get(j - 1).getCity())) {
				topcities.add(ads.get(j).getCity());
				k++;
				if (k == 5) {
					return topcities;
				}
			}
		}
		return topcities;
	}

}