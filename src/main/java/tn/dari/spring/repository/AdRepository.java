package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
	@Query("SELECT count(a) FROM Ad a WHERE a.sell=true and  a.city= :city")
	float retrieSellsveAdsBycity(@Param("city") String role);
}
