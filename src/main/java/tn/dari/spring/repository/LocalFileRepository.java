package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.LocalFile;

@Repository
public interface LocalFileRepository extends JpaRepository<LocalFile, Long>{

}
