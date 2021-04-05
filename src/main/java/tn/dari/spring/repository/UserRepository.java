package tn.dari.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByFirstName(String firstname);

	public User findByLastName(String lastname);

	public Boolean existsByEmail(String email);

	public Boolean existsByUserName(String username);

	public User findByUserName(String username);

	public Optional<User> findByResetToken(String token);

	@Query(nativeQuery = true, value ="Select count(*) From Subscription,Subscription_Ordred,User Where Subscription.subscription_Id=Subscription_Ordred.subscription_subscription_id And User.Id_user=Subscription_Ordred.us_id_user And User.age< :agemax And User.age> :agemin And Subscription.Subscription_Id= :sid")
	public Long UserSubscribeByAge(@Param("agemin") int agemin, @Param("agemax") int agemax, @Param("sid") Long sid )
	
}
