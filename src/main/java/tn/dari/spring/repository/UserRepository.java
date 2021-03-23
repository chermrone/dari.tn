package tn.dari.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByFirstName(String firstname);

	List<User> findByLastName(String lastname);

	Boolean existsByEmail(String email);

	Boolean existsByUserName(String username);

	public User findByUserName(String username);

}
