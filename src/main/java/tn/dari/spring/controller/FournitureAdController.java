package tn.dari.spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import tn.dari.spring.dto.FournitureAdCriteria;
import tn.dari.spring.dto.FournitureAdDto;
import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.service.FournitureAdQueryService;
import tn.dari.spring.service.FournitureAdService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dari/FournitureAd")
public class FournitureAdController {

	@Autowired
	FournitureAdService fournitureAdService;
	@Autowired
	FournitureAdQueryService fournitureAdQueryService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/all")
	public List<FournitureAdDto> getAllFournitureAd() {
		return fournitureAdService.getAllAd().stream()
				.map(fournitureAd -> modelMapper.map(fournitureAd, FournitureAdDto.class)).collect(Collectors.toList());
	}

	@GetMapping("/all/{id}")
	public ResponseEntity<FournitureAdDto> getFournitureAdById(@PathVariable(value = "id") Long faID)
			throws ResourceNotFoundException {
		FournitureAd fournitureAd = fournitureAdService.getAdById(faID);

		FournitureAdDto FournitureAdDto = modelMapper.map(fournitureAd, FournitureAdDto.class);
		return ResponseEntity.ok().body(FournitureAdDto);
	}
	
	@GetMapping("/My/{username}")
	public List<FournitureAdDto> getMyFournitureAd(@PathVariable(value = "username")  String username) {
		return fournitureAdService.getMyAd(username).stream()
				.map(fournitureAd -> modelMapper.map(fournitureAd, FournitureAdDto.class)).collect(Collectors.toList());
	}
	
	@GetMapping("/Other/{username}")
	public List<FournitureAdDto> getOtherFournitureAd(@PathVariable(value = "username") String username) {
		return fournitureAdService.getOtherAd(username).stream()
				.map(fournitureAd -> modelMapper.map(fournitureAd, FournitureAdDto.class)).collect(Collectors.toList());
	}
	@GetMapping("/SearchByCriteria")
	public List<FournitureAdDto> getCriteriaFournitureAd( FournitureAdCriteria fournitureAdCriteria) {
		//LocalDate date = LocalDate.parse("2018-05-05");
		//fournitureAdCriteria.setCreated("2018-05-05");
		return fournitureAdQueryService.findByCriteria(fournitureAdCriteria).stream()
				.map(fournitureAd -> modelMapper.map(fournitureAd, FournitureAdDto.class)).collect(Collectors.toList());
	}
	@GetMapping("/CountByCriteria")
	public Long getCountCriteriaFournitureAd( FournitureAdCriteria fournitureAdCriteria) {
		return fournitureAdQueryService.countByCriteria(fournitureAdCriteria);
	}
	
	
	@GetMapping("/TopSellers")
	public ResponseEntity<List<String>> FindTopFiveSellers()
			throws ResourceNotFoundException {
		
		return ResponseEntity.ok().body(fournitureAdService.FindTopFiveSellers());
	}

	@PostMapping("/add")
	public ResponseEntity postFournitureAd(@Valid @RequestBody FournitureAdDto fournitureAdDto) {

		// convert DTO to entity
		FournitureAd fournitureAdRequest = modelMapper.map(fournitureAdDto, FournitureAd.class);
		if(fournitureAdRequest.getAvailable() == null||fournitureAdRequest.getAvailable() == false){
			fournitureAdRequest.setAvailable(true);
		}
		FournitureAd fournitureAd = null;
		try{
			fournitureAd = fournitureAdService.postAd(fournitureAdRequest);
		}catch(Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		// convert entity to DTO
		FournitureAdDto fournitureAdDtoResponse = modelMapper.map(fournitureAd, FournitureAdDto.class);
		
		return new ResponseEntity<FournitureAdDto>(fournitureAdDtoResponse, HttpStatus.CREATED);

	}

	@PutMapping("/modif/{id}")
	public ResponseEntity<FournitureAdDto> putFournitureAd(@PathVariable(value = "id") Long faID,
			@Valid @RequestBody FournitureAdDto fournitureAdDto) throws ResourceNotFoundException {

		// convert DTO to Entity
		FournitureAd fournitureAdRequest = modelMapper.map(fournitureAdDto, FournitureAd.class);

		FournitureAd fournitureAd = fournitureAdService.putAd(faID, fournitureAdRequest);

		// entity to DTO
		FournitureAdDto FournitureAdDtoResponse = modelMapper.map(fournitureAd, FournitureAdDto.class);

		return ResponseEntity.ok().body(FournitureAdDtoResponse);

	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean> deleteFournitureAd(@PathVariable(value = "id") Long faID)
			throws ResourceNotFoundException {
		return fournitureAdService.deleteAd(faID);
	}
}
