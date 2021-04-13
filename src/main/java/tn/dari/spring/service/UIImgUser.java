package tn.dari.spring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.dari.spring.entity.ImgUser;

public interface UIImgUser {
	public List<ImgUser> retrievalluser();

	public String DeleteImgUser(Long id);

	public ImgUser saveImg(MultipartFile file,String user,String type) throws Exception;
	public ImgUser retrievImage(long id,String imageName) throws Exception;
	public byte[] GetById(long id);

}
