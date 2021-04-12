package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.dari.spring.entity.City;
import tn.dari.spring.entity.User;;

public interface Citiesrepository  extends JpaRepository<City, Long> {
	public City findByName(String name);

}