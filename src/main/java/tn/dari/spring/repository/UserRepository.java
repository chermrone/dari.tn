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

	@Query("select count(s) from Subscription s join s.subord so "
			+ "join so.us u where u.age< :agemax And u.age> :agemin And s.subscriptionId= :sid ")	
	public Long UserSubscribeByAge(@Param("agemin") int agemin, @Param("agemax") int agemax, @Param("sid") Long sid );
	
}
