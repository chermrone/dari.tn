package tn.dari.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.repository.AdRepository;

@Service
public class AdService implements UIadService {

	@Autowired
	AdRepository adrepository;
	@Override
	public String save(Ad ad) {
		adrepository.save(ad);
		return "added successfully";
	}

	@Override
	public String modify(long id) {
		Ad add=adrepository.findById(id).get();
adrepository.save(add);
		return "success modify";
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
		return adrepository.findById(id).get();
	}


	
	
}
