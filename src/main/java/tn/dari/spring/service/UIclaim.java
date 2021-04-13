package tn.dari.spring.service;

import java.util.List;

import tn.dari.spring.entity.Claim;

public interface UIclaim {

	List<Claim> GetAllClaims();

	Claim GetClaimById(Long clmid);

	Claim addClaim(Claim c);

	Claim updateClaim(Claim c);

	void DeleteClaim(Long clmid);

}
