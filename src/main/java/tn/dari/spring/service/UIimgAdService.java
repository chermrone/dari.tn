package tn.dari.spring.service;

import java.util.List;



import org.springframework.web.multipart.MultipartFile;

import tn.dari.spring.entity.ImgAd;

public interface UIimgAdService {
	
	public ImgAd saveImg(MultipartFile file,String ad,String type) throws Exception;
	public ImgAd retrievImage(String imageName);
	public List<ImgAd> retrievall();
	public ImgAd GetById(long id);
	public void Delete(Long id);
}
