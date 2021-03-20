package tn.dari.spring.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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
import tn.dari.spring.service.FournitureAdService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/dari/FournitureAd")
public class FournitureAdController {
	
	@Autowired
	FournitureAdService fournitureAdService;

	@GetMapping("/all")
	public List<FournitureAd> getAllFournitureAd() {
		return fournitureAdService.getAllAd();
	}

	@GetMapping("/all/{id}")
	public ResponseEntity<FournitureAd> getFournitureAdById(@PathVariable(value = "id") Long faID)
			throws ResourceNotFoundException {
		return ResponseEntity.ok().body(fournitureAdService.getAdById(faID));
	}

	@PostMapping("/add")
	public FournitureAd postFournitureAd(@Valid @RequestBody FournitureAd fournitureAd) {
		return fournitureAdService.postAd(fournitureAd);

	}

	@PutMapping("/modif/{id}")
	public ResponseEntity<FournitureAd> putFournitureAd(@PathVariable(value = "id") Long faID,
			@Valid @RequestBody FournitureAd fournitureAd) throws ResourceNotFoundException {
		return ResponseEntity.ok().body(fournitureAdService.putAd(faID, fournitureAd));
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteFournitureAd(@PathVariable(value = "id") Long faID) throws ResourceNotFoundException{
		return fournitureAdService.deleteAd(faID);
	}
}
