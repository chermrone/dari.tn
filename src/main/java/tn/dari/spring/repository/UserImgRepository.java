package tn.dari.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.dari.spring.entity.FilesAd;
import tn.dari.spring.entity.ImgUser;

@Repository
public interface UserImgRepository  extends JpaRepository<ImgUser, Long>  {
	Optional<ImgUser> findByName(String name);

}
