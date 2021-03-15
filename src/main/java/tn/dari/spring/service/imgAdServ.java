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
import tn.dari.spring.entity.ImgAd;
import tn.dari.spring.repository.ImgAdRepository;


@Service
public class imgAdServ implements UIimgAdService{

	@Autowired

	private ImgAdRepository  imageRepository;
	@Override
	public ImgAd saveImg(MultipartFile file,String ad) throws Exception {
Ad adJson = new Ad();
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			adJson = objectMapper.readValue(ad, Ad.class);
			System.out.println("Ad json=> objet"+adJson);
		} catch (IOException err) {
			System.out.printf("Error", err.toString());
		}
		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		ImgAd img = new ImgAd(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()),adJson);
		System.out.println(file.getOriginalFilename());
		if(file.getOriginalFilename().toLowerCase().indexOf(".jpeg")!=-1||
				file.getOriginalFilename().toLowerCase().indexOf(".jpg")!=-1||
				file.getOriginalFilename().toLowerCase().indexOf(".png")!=-1||
				file.getOriginalFilename().toLowerCase().indexOf(".gif")!=-1
				||file.getOriginalFilename().toLowerCase().indexOf(".tif")!=-1||
				file.getOriginalFilename().toLowerCase().indexOf(".svg")!=-1)
		 return imageRepository.save(img);
		else 
		{  throw (new Exception("Vous devez entrer une image")) ;
	}
	}
	@Override
	  public ImgAd retrievImage(String imageName){
	final Optional<ImgAd> retrievedImage = imageRepository.findByName(imageName);
	ImgAd img = new ImgAd(retrievedImage.get().getName(), retrievedImage.get().getType(),
			decompressBytes(retrievedImage.get().getPicByte()));
			return img;
	}
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

		// uncompress the image bytes before returning it to the angular application
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
		public List<ImgAd> retrievall() {
			
			return imageRepository.findAll();
		}
@Override
public ImgAd GetById(long id) {
ImgAd img=	imageRepository.findById(id).get();
	return img ;
}
@Override
public void Delete(Long id) {
	imageRepository.deleteById(id);
	
}
		

}
		
	
	

















































/*public Ad store(MultipartFile file,Ad ad) throws IOException {
		
		
		
		
		  
		  

		  Ad adJson = new Ad();
			try {
				ObjectMapper objectMapper = new ObjectMapper();
		        ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(ad);
	            System.out.println(jsonInString);
	            adJson = objectMapper.readValue(jsonInString, Ad.class);
			} catch (IOException err) {
				System.out.printf("Error", err.toString());
			}
			
			int fileCount = file.size();
			adJson.setCount(fileCount);
			
			return adJson;
		  
		  
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	
	    ImgAd FileDB = new ImgAd(fileName, file.getContentType(), file.getBytes());

	    return fileDBRepository.save(adJson);
	  }

	  public ImgAd getFile(long id) {
	    return fileDBRepository.findById(id).get();
	  }
	  
	 
*/
