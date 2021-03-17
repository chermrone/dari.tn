package tn.dari.spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.dari.spring.entity.ImgUser;
import tn.dari.spring.service.UIFileService;
import tn.dari.spring.service.UIImgUser;

@RestController
@CrossOrigin("*")
@RequestMapping("/dari/imgusers")
public class ImgUserController {

	
	 @Autowired
	  private UIImgUser imgService;

		@PostMapping(value="/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,
				 MediaType.MULTIPART_FORM_DATA_VALUE })
		public ResponseEntity<List<String>>uplaodImage(@RequestParam("imageFile") MultipartFile[] files,@RequestPart() String user) throws Exception {
		
			List<String> fileNames = new ArrayList<>();

		      Arrays.asList(files).stream().forEach(file -> {
		    	  try {
					imgService.saveImg(file,user,null);fileNames.add(file.getOriginalFilename());
				} catch (Exception e) {
			//return new ResponseEntity<String>("error",HttpStatus.OK);

					e.printStackTrace();
					
				}
		        
		      });
		      System.out.println(fileNames);
	if(!fileNames.isEmpty())
			return new ResponseEntity<List<String>>(fileNames,HttpStatus.OK);
	else  		return new ResponseEntity<List<String>>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		

		@GetMapping(path = { "/getname/{imageName}" })
		public ImgUser getImageByName(@PathVariable("imageName") String imageName) throws Exception {
			ImgUser img=(ImgUser) imgService.retrievImage(imageName);

			return img;
		}
		
		
		

		@GetMapping(path = { "/getid/{id}" })
		public ImgUser getImageById(@PathVariable("id") long id) throws IOException {
			ImgUser img=(ImgUser)imgService.GetById(id);

			return img;
		}
		@GetMapping(path = { "/all" })
		public List<ImgUser> getAll() throws IOException {
			List<ImgUser> img=imgService.retrievalluser();

			return img;
		}
		@DeleteMapping("/delete/img/{id}")
		public String delete(@PathVariable("id") Long id)  throws Exception  {
			System.out.println("delete");
		return 	imgService.DeleteUser(id);
		}


	
}
