package tn.dari.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import tn.dari.spring.entity.ImgAd;

@Repository
public interface ImgAdRepository extends JpaRepository<ImgAd, Long> {
	Optional<ImgAd> findByName(String name);
}
