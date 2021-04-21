package tn.dari.spring.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.Optional;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.FilesAd;
import tn.dari.spring.entity.ImgUser;
import tn.dari.spring.entity.User;
import tn.dari.spring.exception.AdNotFoundException;

import tn.dari.spring.repository.FilesAdRepository;
import tn.dari.spring.repository.UserImgRepository;

@Service
public class FileServ implements UIFileService {

	@Autowired
	private FilesAdRepository imageRepository;
	@Autowired
	UserImgRepository userRep;

	@Override
	public Object saveImg(MultipartFile file, String object, String type) throws Exception {
		Ad adJson = new Ad();
		User UserJson = new User();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			UserJson = objectMapper.readValue(object, User.class);
			System.out.println("user json=> objet" + UserJson);
		} catch (IOException err) {
			System.out.printf("Error it isnt a user", err.toString());
		}
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			adJson = objectMapper.readValue(object, Ad.class);
			System.out.println("Ad json=> objet" + adJson);
		} catch (IOException err) {
			System.out.printf("Error it isnt an ad ", err.toString());
		}

		// when it is an ad
		if (adJson.getAdId() != null) {

			System.out.println("Original" + type + " Byte Size - " + file.getBytes().length);
			FilesAd imgad = new FilesAd(file.getOriginalFilename(), file.getContentType(),
					file.getBytes(), adJson);
			System.out.println("instanciation ad +img");

			// check if it is an image or no
			System.out.println(file.getOriginalFilename());
			if (type.equals("image")) {
				if (file.getOriginalFilename().toLowerCase().indexOf(".jpeg") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".jpg") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".png") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".gif") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".tif") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".svg") != -1) {
					System.out.println("image");
					return imageRepository.save(imgad);

				} else
					throw (new Exception("you need to enter a photo "));
			}

			// check if it a video or no
			else if (type.equals("video")) {

				{
					if (file.getOriginalFilename().toLowerCase().indexOf(".mov") != -1
							|| file.getOriginalFilename().toLowerCase().indexOf(".avi") != -1
							|| file.getOriginalFilename().toLowerCase().indexOf(".mp4") != -1
							|| file.getOriginalFilename().toLowerCase().indexOf(".flv") != -1
							|| file.getOriginalFilename().toLowerCase().indexOf(".wmv") != -1) {
						System.out.println("video");

						return imageRepository.save(imgad);
					} else
						throw (new Exception("you need to enter a video "));

				}

			}
		}
		// when it is a user
		else if (UserJson.getIdUser() != null) {
			ImgUser imguser = new ImgUser(file.getOriginalFilename(), file.getContentType(),
					file.getBytes(), UserJson);
			System.out.println("instanciation user +img");

			// check if it is an image or no
			System.out.println(file.getOriginalFilename());
			if (type == null) {
				if (file.getOriginalFilename().toLowerCase().indexOf(".jpeg") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".jpg") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".png") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".gif") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".tif") != -1
						|| file.getOriginalFilename().toLowerCase().indexOf(".svg") != -1) {
					System.out.println("image");
					return userRep.save(imguser);

				} else
					throw (new Exception("you need to enter a photo "));
			}

		}

		throw (new Exception("Vous devez entrer une image ou un video "));

	}

	@Override
	public Object retrievImage(long id,String imageName) throws Exception {
		try {
			final Optional<FilesAd> retrievedImage = imageRepository.findByName(imageName);
			FilesAd img = new FilesAd(retrievedImage.get().getName(), retrievedImage.get().getType(),
					retrievedImage.get().getPicByte());
			return retrievedImage.get().getPicByte();
		} catch (Exception e) {
			System.out.println("it isnt an img ad");
		}
		try {
			final Optional<ImgUser> retrievedImage = userRep.findByName(imageName);
			ImgUser img = new ImgUser(retrievedImage.get().getName(), retrievedImage.get().getType(),
					retrievedImage.get().getPicByte());
			return retrievedImage.get().getPicByte();
		} catch (Exception e) {
			System.out.println("it isnt a user img");
		}
		throw (new Exception("Erreur "));

	}

	@Override
	public List<FilesAd> retrievallad() {
List<byte[]> img = null;
		List<FilesAd> files= imageRepository.findAll();System.out.println("hellooooo");
		return files;
	}

	@Override
	public byte[] GetById(long id) {
	
			 FilesAd img = imageRepository.findById(id)
					.orElseThrow(() -> new AdNotFoundException(" id= " + id + " is not found"));
			return  img.getPicByte();
	
			
	}

	@Override
	public void DeleteAd(Long id) {
		imageRepository.deleteById(id);

	}
	@Override
	public List<byte[]> GetByAdId(Long id) {
		return imageRepository.findByAdId(id);
	}
	

}