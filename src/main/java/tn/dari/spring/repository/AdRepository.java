package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

}
