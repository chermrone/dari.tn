package tn.dari.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.DeliveryMan;

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long> {
	
	List<DeliveryMan> findByGouvernerat(String gouvernerat);

}
