package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Subscription;
import tn.dari.spring.enumeration.SubscriptionType;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	public Subscription save(Long id);
	public Subscription findBySubscriptiontype(SubscriptionType subt);

}
