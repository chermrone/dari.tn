package tn.dari.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import tn.dari.spring.entity.FilesAd;
import tn.dari.spring.service.UIFileService;

import org.springframework.http.MediaType;

@RestController
@CrossOrigin("*")
@RequestMapping("/dari/imgads")
public class ImgAdController {

	  @Autowired
	  private UIFileService imgService;
		@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
		@PostMapping(value="/upload/{type}", consumes = { MediaType.APPLICATION_JSON_VALUE,
				 MediaType.MULTIPART_FORM_DATA_VALUE })
		public ResponseEntity<List<String>>uplaodImage(@RequestParam("imageFile") MultipartFile[] files,@RequestPart() String ad,@PathVariable String type) throws Exception {
		
			List<String> fileNames = new ArrayList<>();

		      Arrays.asList(files).stream().forEach(file -> {
		    	  try {
					imgService.saveImg(file,ad,type);fileNames.add(file.getOriginalFilename());
				} catch (Exception e) {
			//return new ResponseEntity<String>("error",HttpStatus.OK);

					e.printStackTrace();
					
				}
		        
		      });
	if(!fileNames.isEmpty())
			return new ResponseEntity<List<String>>(fileNames,HttpStatus.OK);
	else  		return new ResponseEntity<List<String>>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		@PreAuthorize("hasAuthority('ADMIN')")

		@GetMapping(path = { "/getname/{idAd}/{imageName}" })
		public byte[] getImageByName(@PathVariable("idAd") long idAd,@PathVariable("imageName") String imageName) throws Exception {
			byte[] img= (byte[]) imgService.retrievImage(idAd,imageName);

			return img;
		}
		
		
		

		@GetMapping(path = { "/getid/{id}" })
		public byte[] getImageById(@PathVariable("id") long id) throws IOException {
			byte[] img=(byte[])imgService.GetById(id);

			return img;
		}
		@PreAuthorize("hasAuthority('ADMIN')")

		@GetMapping(path = { "/all" })
		public List<FilesAd> getAll() throws IOException {
			return imgService.retrievallad();
		}
		@PreAuthorize(" hasAuthority('SELLER')")

		@DeleteMapping("/delete/img/{id}")
		public ResponseEntity<String> delete(@PathVariable("id") Long id) throws Exception  {
			imgService.DeleteAd(id);
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}


}
