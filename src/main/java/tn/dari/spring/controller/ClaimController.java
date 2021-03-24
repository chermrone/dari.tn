package tn.dari.spring.controller;

import java.util.List;

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

import lombok.var;
import tn.dari.spring.entity.Claim;
import tn.dari.spring.entity.User;
import tn.dari.spring.service.UIclaim;

@CrossOrigin("*")
@RestController
@RequestMapping("/dari/Claims")
public class ClaimController {
	@Autowired
	UIclaim claim;

	@GetMapping("/all")
	public ResponseEntity<List<Claim>> getAllClaims() {
		System.out.println("reception de la requete");
		List<Claim> cl = claim.GetAllClaims();
		return new ResponseEntity<List<Claim>>(cl, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Claim> Getbyid(@PathVariable("id") Long clmid) {
		Claim cl = claim.GetClaimById(clmid);
		return new ResponseEntity<Claim>(cl, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Claim> save(@RequestBody Claim cl) {
		List<Claim> allclaim = claim.GetAllClaims();
		for (Claim claim : allclaim) {
			if (claim.getClmId().equals(cl.getClmId())) {
				return new ResponseEntity<Claim>(HttpStatus.NOT_ACCEPTABLE);
			}

		}
		int nb_of_claims = allclaim.size();
		Claim claone = claim.addClaim(cl);
		if (nb_of_claims == 9) {
			User us = cl.getAd().getUs();
			us.setUserState(false);
		}

		return new ResponseEntity<Claim>(claone, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Claim> update(@RequestBody Claim c) {
		Claim cl = claim.updateClaim(c);
		return new ResponseEntity<Claim>(cl, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	void deleteEmployee(@PathVariable("id") Long clmid) {
		claim.DeleteClaim(clmid);
	}
}
