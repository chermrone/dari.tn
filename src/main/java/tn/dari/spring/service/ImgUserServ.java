package tn.dari.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.dari.spring.entity.ImgUser;
import tn.dari.spring.exception.AdNotFoundException;
import tn.dari.spring.repository.UserImgRepository;

@Service
public class ImgUserServ implements UIImgUser {
	@Autowired UserImgRepository userRep;
	
	@Autowired FileServ fileserv;
	
	@Override
	public List<ImgUser> retrievalluser() {
		
		return userRep.findAll();
	}
	
	@Override		
	public String DeleteImgUser(Long id)  {
		System.out.println("hello");
		userRep.deleteById(id);
		return "success";
	}

	@Override
	public ImgUser saveImg(MultipartFile file, String user, String type) throws Exception {
		return (ImgUser) fileserv.saveImg(file, user, type);
	}


	@Override
	public ImgUser retrievImage(long id,String imageName) throws Exception {
		return (ImgUser) fileserv.retrievImage(id, imageName);
	}
	@Override
	public byte[] GetById(long id) {
		
		ImgUser img = userRep.findById(id).orElseThrow(() -> new AdNotFoundException(" id= " + id + " is not found"));
		return FileServ.decompressBytes(img.getPicByte());

	}
}
