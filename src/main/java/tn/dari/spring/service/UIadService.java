package tn.dari.spring.service;

import java.util.List;
import tn.dari.spring.entity.Ad;
import tn.dari.spring.enumeration.TypeBatiment;
import tn.dari.spring.enumeration.Typead;

public interface UIadService {
	public Ad save(Ad ad);

	public void Delete(long id);

	public List<Ad> getAll( );

	public Ad getById(long id);
	
	public float getBuyedHousesByCity(String city);
	
	public float getBuyedHousesByCityAndMaxprice(String city, double price);
	
	public float getBuyedHousesByCityAndMinprice(String city, double price);
	
	public float getBuyedHousesByCityInPeriod(String city, int period);
	
	public List<String> ordercitiesByBuyingdesc();
	
	public List<String> topfivecities();

	public Ad BuyedHouse(long id);

	public double EstimatedHouse(Ad ad);

	public List<Ad> GetAdsOwned();

	public Ad modify(Ad ad);


	Ad GetAdOwned(long id);

	public String SituationAd(long id);

	int getNumberOfFavoriteAd(long id);

	List<Ad> retriveAdUsingRole(long id);

	List<Ad> retrieveAdsByBannedUser(Long role, java.util.Date fromDate, java.util.Date toDate);

	void UpdateEstimatedPeriodSellHouse();
	void UpdateTopFiveInTableCity();
	public int EstimatedPeriodSellHouse(Ad ad);

	Ad GetAdLast();

	public List<Ad> GetAdsRent();

	public List<Ad> GetAdsSell();

	//public void savFav(long id);

	public List<Ad> ReetreivefavOwned();

	void savFav(long id, String us);

	public void Deletefav(Long id);

	public List<Ad> getAdByCaracteristic(Typead typeAd, TypeBatiment typebat, double price,int rooms, String city);

	public String[] retrieveFavCity(long id);

	List<Ad> MostCheapestAdSELL();

	List<Ad> MostCheapestAdRENT();



}
