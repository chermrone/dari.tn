package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

}
