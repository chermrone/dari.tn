package tn.dari.spring.service;

import java.util.List;

import tn.dari.spring.entity.Claim;

public interface UIclaim {

	public List<Claim> GetAllClaims();

	public Claim GetClaimById(Long clmid);

	public Claim addClaim(Claim c);

	public Claim updateClaim(Claim c);

	public void DeleteClaim(Long clmid);

}
