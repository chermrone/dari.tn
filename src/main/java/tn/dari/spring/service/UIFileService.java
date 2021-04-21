package tn.dari.spring.service;

import java.util.List;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import tn.dari.spring.entity.FilesAd;
import tn.dari.spring.entity.ImgUser;

public interface UIFileService {
	
	public Object saveImg(MultipartFile file,String ad,String type) throws Exception;
	public Object retrievImage(long id,String imageName) throws Exception;
	public List<FilesAd> retrievallad();
	

	public Object GetById(long id);
	
	public void DeleteAd(Long id);
	List<byte[]> GetByAdId(Long id);

}
