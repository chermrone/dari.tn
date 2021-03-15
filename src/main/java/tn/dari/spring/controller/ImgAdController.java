package tn.dari.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
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

import tn.dari.spring.entity.ImgAd;
import tn.dari.spring.service.UIimgAdService;

import org.springframework.http.MediaType;

@RestController
@CrossOrigin("*")
@RequestMapping("/dari/imgads")
public class ImgAdController {

	  @Autowired
	  private UIimgAdService imgService;

		@PostMapping(value="/upload/{type}", consumes = { MediaType.APPLICATION_JSON_VALUE,
				 MediaType.MULTIPART_FORM_DATA_VALUE })
		public ResponseEntity<List<String>>uplaodImage(@RequestParam("imageFile") MultipartFile[] files,@RequestPart() String ad,@PathVariable String type) throws Exception {
		
			List<String> fileNames = new ArrayList<>();

		      Arrays.asList(files).stream().forEach(file -> {
		    	  try {
					imgService.saveImg(file,ad);
				} catch (Exception e) {
			//return new ResponseEntity<String>("error",HttpStatus.OK);

					e.printStackTrace();
					
				}
		        
		      });
	if(!fileNames.isEmpty())
			return new ResponseEntity<List<String>>(fileNames,HttpStatus.OK);
	else  		return new ResponseEntity<List<String>>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		

		@GetMapping(path = { "/getname/{imageName}" })
		public ImgAd getImageByName(@PathVariable("imageName") String imageName) throws IOException {
			ImgAd img=imgService.retrievImage(imageName);

			return img;
		}
		
		
		

		@GetMapping(path = { "/getid/{id}" })
		public ImgAd getImageById(@PathVariable("id") long id) throws IOException {
			ImgAd img=imgService.GetById(id);

			return img;
		}
		@GetMapping(path = { "/all" })
		public List<ImgAd> getAll() throws IOException {
			List<ImgAd> img=imgService.retrievall();

			return img;
		}
		@DeleteMapping("/delete/img/{id}")
		public ResponseEntity<String> delete(@PathVariable("id") Long id) {
			imgService.Delete(id);
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}


}
