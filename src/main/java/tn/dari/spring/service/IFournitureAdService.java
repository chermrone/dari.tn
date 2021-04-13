package tn.dari.spring.service;
import java.util.List;
import java.util.Map;

import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.exception.ResourceNotFoundException;

public interface IFournitureAdService {
	public List<FournitureAd> getAllAd();

	public FournitureAd getAdById(Long faID) throws ResourceNotFoundException;

	public FournitureAd postAd(FournitureAd fournitureAd);

	public FournitureAd putAd(Long faID, FournitureAd fournitureAd) throws ResourceNotFoundException;

	public Map<String, Boolean> deleteAd(Long faID) throws ResourceNotFoundException;
	
	
	public List<FournitureAd> getMyAd(String username);
	public List<FournitureAd> getOtherAd(String username);
	public List<String> FindTopFiveSellers();

}