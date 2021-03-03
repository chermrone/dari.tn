package tn.dari.spring.service;

import java.util.List;

import tn.dari.spring.entity.Ad;

public interface UIadService  {
	String save(Ad ad);
	String modify(long id);
	String Delete(long id);
	List<Ad> getAll();
	Ad getById(long id);
	
}
