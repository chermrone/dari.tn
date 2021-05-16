package tn.dari.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.FilesAd;

@Repository
public interface FilesAdRepository extends JpaRepository<FilesAd, Long> {
	public Optional<FilesAd> findByName(String name);
	@Query("SELECT a.picByte FROM FilesAd a join a.ad aa WHERE aa.adId=:id")
	public List<byte[]> findByAdId(@Param("id") long id);
	public List<FilesAd> findByAdAndType(Ad ad, String string);
	
}
