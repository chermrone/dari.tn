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
public class FileServ implements UIFileService{

	@Autowired
	private FilesAdRepository  imageRepository;
	@Autowired UserImgRepository userRep;
	@Override
	public Object saveImg(MultipartFile file,String object,String type) throws Exception {
Ad adJson = new Ad();
User UserJson=new User();		
try {
	ObjectMapper objectMapper = new ObjectMapper();
	UserJson = objectMapper.readValue(object, User.class);
	System.out.println("user json=> objet"+UserJson);
} catch (IOException err) {
	System.out.printf("Error it isnt a user", err.toString());
}
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			adJson = objectMapper.readValue(object, Ad.class);
			System.out.println("Ad json=> objet"+adJson);
		} catch (IOException err) {
			System.out.printf("Error it isnt an ad ", err.toString());
		}
		
		
		//when it is an ad
		if(adJson.getAdId()!=null)		
		{
		
		System.out.println("Original"+ type+" Byte Size - " + file.getBytes().length);
	FilesAd imgad= new FilesAd(file.getOriginalFilename(), file.getContentType(),compressBytes(file.getBytes(),type),adJson);
		System.out.println("instanciation ad +img");
		
		
		
		//check if it is an image or no
		System.out.println(file.getOriginalFilename());
		  if(type.equals("image"))
		  {
			  if(file.getOriginalFilename().toLowerCase().indexOf(".jpeg")!=-1||
			file.getOriginalFilename().toLowerCase().indexOf(".jpg")!=-1||
			file.getOriginalFilename().toLowerCase().indexOf(".png")!=-1||
			file.getOriginalFilename().toLowerCase().indexOf(".gif")!=-1
			||file.getOriginalFilename().toLowerCase().indexOf(".tif")!=-1||
			file.getOriginalFilename().toLowerCase().indexOf(".svg")!=-1)
			  {
				  System.out.println("image");	
				  return imageRepository.save(imgad);

			  }
			  else 
				   throw (new Exception("you need to enter a photo ")) ;
		  }
		  
	//check if it a video or no	  
	    	 else if(type.equals("video"))
	    		  {

	    			  {if(file.getOriginalFilename().toLowerCase().indexOf(".mov")!=-1||
	    				file.getOriginalFilename().toLowerCase().indexOf(".avi")!=-1||
	    				file.getOriginalFilename().toLowerCase().indexOf(".mp4")!=-1||
	    				file.getOriginalFilename().toLowerCase().indexOf(".flv")!=-1
	    				||file.getOriginalFilename().toLowerCase().indexOf(".wmv")!=-1)
	    				  {
	    				  System.out.println("video");

	    				  return imageRepository.save(imgad);
	    				  }
	    			  else 
	    				   throw (new Exception("you need to enter a video ")) ;
	    				 
	    			  }
	    				
	    			  
	    		  }		
		}
		//when it is a user
		else if (UserJson.getIdUser()!=null)
		{
			ImgUser imguser = new ImgUser(file.getOriginalFilename(), file.getContentType(),compressBytes(file.getBytes(),type),UserJson);
		System.out.println("instanciation user +img");
		
		
		//check if it is an image or no
		System.out.println(file.getOriginalFilename());
		  if(type==null)
		  {
			  if(file.getOriginalFilename().toLowerCase().indexOf(".jpeg")!=-1||
			file.getOriginalFilename().toLowerCase().indexOf(".jpg")!=-1||
			file.getOriginalFilename().toLowerCase().indexOf(".png")!=-1||
			file.getOriginalFilename().toLowerCase().indexOf(".gif")!=-1
			||file.getOriginalFilename().toLowerCase().indexOf(".tif")!=-1||
			file.getOriginalFilename().toLowerCase().indexOf(".svg")!=-1)
			  {
				  System.out.println("image");	
				  return userRep.save(imguser);

			  }
			  else 
				   throw (new Exception("you need to enter a photo ")) ;
		  }
		  
		
		}
		
	
		
		   throw (new Exception("Vous devez entrer une image ou un video ")) ;
		
	
	}
	@Override
	  public Object retrievImage(String imageName) throws Exception{
		try {
			final Optional<FilesAd> retrievedImage = imageRepository.findByName(imageName);
			FilesAd img = new FilesAd(retrievedImage.get().getName(), retrievedImage.get().getType(),
					decompressBytes(retrievedImage.get().getPicByte()));
					return img;
		} catch (Exception e) {
			System.out.println("it isnt an img ad");
		}
		try {
			final Optional<ImgUser> retrievedImage = userRep.findByName(imageName);
			ImgUser img = new ImgUser(retrievedImage.get().getName(), retrievedImage.get().getType(),
					decompressBytes(retrievedImage.get().getPicByte()));
					return img;
		} catch (Exception e) {
			System.out.println("it isnt a user img");
		}
		   throw (new Exception("Erreur ")) ;

	
	}
		// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data,String type) {
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
			System.out.println("Compressed "+ type+ " Byte Size - " + outputStream.toByteArray().length);

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
			}System.out.println("deCompressed Byte Size - " + outputStream.toByteArray().length);
			return outputStream.toByteArray();
		}
@Override
		public List<FilesAd> retrievallad() {
			
			return imageRepository.findAll();
		}

@Override
public Object GetById(long id) {
	Object img = null;
try { img=(FilesAd) img;
	 img=	imageRepository.findById(id).orElseThrow(() -> new AdNotFoundException(" id= " + id + " is not found"));
	return img ;
} catch (Exception e) {
	// TODO: handle exception
}


try {img = (ImgUser)img;
	 img=	userRep.findById(id).orElseThrow(() -> new AdNotFoundException(" id= " + id + " is not found"));
	return img ;
} catch (Exception e) {
	// TODO: handle exception
}
return img;
}
@Override
public void DeleteAd(Long id) {
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
