package tn.dari.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import tn.dari.spring.entity.FilesAd;

@Repository
public interface FilesAdRepository extends JpaRepository<FilesAd, Long> {
	public Optional<FilesAd> findByName(String name);
}
