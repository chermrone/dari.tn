package tn.dari.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.SubscriptionOrdred;
import tn.dari.spring.entity.User;
@Repository
public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrdred, Long> {
	public List<SubscriptionOrdred> findByEnable(boolean enable);
	public List<SubscriptionOrdred> findByUs(User us);

}
