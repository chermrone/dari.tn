package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.LocalFile;

@Repository
public interface LocalFileRepository extends JpaRepository<LocalFile, Long> {

	@Modifying
	@Query(value = "DELETE FROM LocalFile lf WHERE lf.path = :path")
	int deleteByPath(@Param("path") String path);

}
