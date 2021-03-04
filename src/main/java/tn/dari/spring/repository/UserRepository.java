package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
public User FindByFirstName(String firstname);
public User FindByLastName(String lastname);
public  User FindByUserName(String username);

}
