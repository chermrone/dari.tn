package tn.dari.spring.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;


import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.FournitureAdRepository;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dari/FournitureAd")
public class FournitureAdController {
	@Autowired
	FournitureAdRepository fournitureAdRepository;
	
	@GetMapping("/all")
	public List<FournitureAd> getAllFournitureAd(){
		return fournitureAdRepository.findAll();
	}
	
	@GetMapping("/all/{id}")
	public ResponseEntity<FournitureAd> getFournitureAdById(@PathVariable(value="id")  Long faID )
	throws ResourceNotFoundException {
		FournitureAd fournitureAd = fournitureAdRepository.findById(faID)
				.orElseThrow(()-> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: "+faID) );
		return ResponseEntity.ok().body(fournitureAd);
				}
	
	@PostMapping("/add")
	public FournitureAd  postFournitureAd(@Valid @RequestBody FournitureAd fournitureAd){
		Date date = new Date();
		fournitureAd.setCreated(date);
		fournitureAdRepository.save(fournitureAd);
		return fournitureAd;
			
	}
	@PutMapping("/modif/{id}")
	public ResponseEntity<FournitureAd>  putFournitureAd(@PathVariable(value="id")  Long faID ,@Valid @RequestBody FournitureAd fournitureAd)
	throws ResourceNotFoundException{
		if(fournitureAd.getFaID() == faID){
			FournitureAd fournitureAd1 = fournitureAdRepository.findById(faID)
					.orElseThrow(()-> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: "+faID) );
					
		Date date = new Date();
		fournitureAd.setCreated(date);
		fournitureAdRepository.save(fournitureAd);
		}
		return ResponseEntity.ok().body(fournitureAd);
	}
	
	@DeleteMapping("/delete/{id}")
	public Map<String,Boolean> deleteFournitureAd(@PathVariable(value="id")  Long faID) 
	throws ResourceNotFoundException{
		FournitureAd fournitureAd = fournitureAdRepository.findById(faID)
				.orElseThrow(()-> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: "+faID) );
		fournitureAdRepository.delete(fournitureAd);
		Map<String,Boolean> response = new HashMap<>();
		response.put("FournitureAd deleted : ", Boolean.TRUE);
		return response;
	}

}
