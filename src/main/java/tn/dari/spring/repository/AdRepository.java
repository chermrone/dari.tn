package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
	@Query("SELECT count(a) FROM Ad a WHERE a.sell=true and  a.city= :city")
	float retrieveSellsAdsBycity(@Param("city") String role);
	
	@Query("SELECT count(a) FROM Ad a WHERE a.sell=true and a.city= :city and a.price< :price")
	float retrieveSellsAdsByCityMaxPrice(@Param("city") String role, @Param("price") double price);
	
	@Query("SELECT count(a) FROM Ad a WHERE a.sell=true and a.city= :city and a.price> :price")
	float retrieveSellsAdsByCityMinPrice(@Param("city") String role, @Param("price") double price);

	@Query("SELECT count(a) FROM Ad a WHERE a.city= :city and a.sell=true and  DATEDIFF(a.BuyingDate,a.creationDate)< :period")
	float retrieveSellsAdsInPeriod(@Param("city") String city, @Param("period") int period);

}
