package tn.dari.spring.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.ImgAd;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.User;

import tn.dari.spring.exception.UserNotFoundException;
import tn.dari.spring.repository.UserRepository;

@Service
public class UserService implements UIuser {
	@Autowired
	UserRepository ur;

	@Override
	public List<User> GetAllUsers() {

		return ur.findAll();
	}

	@Override
	public User GetUserById(Long id) {
		return ur.findById(id).get();
		//return ur.findById(id).orElseThrow(() -> new UserNotFoundException("user by id= " + id + " was not found"));
	}

	@Override
	public User GetUserByFirstName(String firstname) {

		return ur.findByFirstName(firstname);
	}

	@Override
	public User GetUserByLastName(String lastname) {

		return ur.findByLastName(lastname);
	}

	@Override
	public User GetUserByUserName(String username) {

		return ur.findByUserName(username);//.get();
	}

	@Override
	public User AddUser(User u) {

		return ur.save(u);
	}

	@Override
	public User UpdateUser(User user) {

		return ur.save(user);
	}

	@Override
	public void DeleteUser(Long id) {
		ur.deleteById(id);

	}

/*	@Override
	public User saveImg(MultipartFile file, String us) throws Exception {
User usJson = new User();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			usJson = objectMapper.readValue(us, User.class);
			System.out.println("Ad json=> objet"+usJson);
		} catch (IOException err) {
			System.out.printf("Error", err.toString());
		}
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		User img = new User(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()),usJson);
		System.out.println(file.getOriginalFilename());
		if(file.getOriginalFilename().toLowerCase().indexOf(".jpeg")!=-1||
				file.getOriginalFilename().toLowerCase().indexOf(".jpg")!=-1||
				file.getOriginalFilename().toLowerCase().indexOf(".png")!=-1||
				file.getOriginalFilename().toLowerCase().indexOf(".gif")!=-1
				||file.getOriginalFilename().toLowerCase().indexOf(".tif")!=-1||
				file.getOriginalFilename().toLowerCase().indexOf(".svg")!=-1)
			
		 return us.save(img);
		else 
		{  throw (new Exception("Vous devez entrer une image")) ;
	}
	}
	*/
		// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

			return outputStream.toByteArray();
	}

		public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
					
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}System.out.println("deCompressed Image Byte Size - " + outputStream.toByteArray().length);
			return outputStream.toByteArray();
		}

	

	
	@Override
	public void Deleteimg(String imgname) {
	ur.Deleteimgbyname(imgname);
		
	}

}
