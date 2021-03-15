package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.CreditSimulator;

@Repository
public interface CreditSimulatorRepository extends JpaRepository<CreditSimulator, Long> {

}
