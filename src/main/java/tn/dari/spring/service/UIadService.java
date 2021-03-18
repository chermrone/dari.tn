package tn.dari.spring.service;

import java.util.List;

import tn.dari.spring.entity.Ad;

public interface UIadService {
	Ad save(Ad ad);

	Ad modify(long id);

	String Delete(long id);

	List<Ad> getAll();

	Ad getById(long id);
	
	float getBuyedHousesByCity(String city);
	
	float getBuyedHousesByCityAndMaxprice(String city, double price);
	
	float getBuyedHousesByCityAndMinprice(String city, double price);

}
