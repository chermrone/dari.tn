package tn.dari.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	Subscription save(Long id);

	List<Subscription> findByTitle(String title);

	List<Subscription> findByPrice(double price);
	
	List<Subscription> findByTitleAndPrice(String title, double price);
}
