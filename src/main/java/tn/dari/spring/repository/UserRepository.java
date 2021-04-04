package tn.dari.spring.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
