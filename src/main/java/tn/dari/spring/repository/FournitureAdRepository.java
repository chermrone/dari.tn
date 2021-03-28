package tn.dari.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.FournitureAd;
import java.lang.String;
import java.util.List;

@Repository
public interface FournitureAdRepository extends JpaRepository<FournitureAd, Long> , JpaSpecificationExecutor<FournitureAd>{
	
	List<FournitureAd> findByUserName(String username);
	List<FournitureAd> findByUserNameNotLike(String username);
	


}
