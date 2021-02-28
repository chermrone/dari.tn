package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
