package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.OrderUser;

@Repository
public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {

}
