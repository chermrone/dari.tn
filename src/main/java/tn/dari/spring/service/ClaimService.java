package tn.dari.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Claim;
import tn.dari.spring.entity.User;
import tn.dari.spring.exception.ClaimNotFound;
import tn.dari.spring.exception.UserNotFoundException;
import tn.dari.spring.repository.ClaimRepository;
@Service
public class ClaimService implements UIclaim {
@Autowired
ClaimRepository cl;
@Autowired
UserService us;
	@Override

	public List<Claim> GetAllClaims() {
		
		return cl.findAll();
	}

	@Override
	
	public Claim GetClaimById(Long clmid) {
		
		return cl.findById(clmid).orElseThrow(() -> new ClaimNotFound("claim by id= " + clmid + " claim not found"));
	}

	@Override
	
	public Claim addClaim(Claim c) {
		User user = c.getAd().getUs();
		System.out.println(user);
		us.BanUser1(user.getIdUser());
		return cl.save(c);
	}

	@Override

	public Claim updateClaim(Claim c) {
		
		return cl.save(c) ;
	}

	@Override
	
	public void DeleteClaim(Long clmid) {
		cl.deleteById(clmid);
		
	}

}
