package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.FournitureAd;

@Repository
public interface FournitureAdRepository extends JpaRepository<FournitureAd, Long> {

}
