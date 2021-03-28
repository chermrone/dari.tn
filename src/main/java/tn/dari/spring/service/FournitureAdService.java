package tn.dari.spring.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.exception.ResourceNotFoundException;
import tn.dari.spring.repository.FournitureAdRepository;

@Service
public class FournitureAdService implements IFournitureAdService {

	@Autowired
	FournitureAdRepository fournitureAdRepository;

	@Override
	public List<FournitureAd> getAllAd() {
		return fournitureAdRepository.findAll();
	}

	@Override
	public FournitureAd getAdById(Long faID) throws ResourceNotFoundException {
		FournitureAd fournitureAd = fournitureAdRepository.findById(faID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + faID));
		return fournitureAd;
	}

	@Override
	public FournitureAd postAd(FournitureAd fournitureAd) {
		Date date = new Date();
		fournitureAd.setCreated(date);
		fournitureAdRepository.save(fournitureAd);
		return fournitureAd;
	}

	@Override
	public FournitureAd putAd(Long faID, FournitureAd fournitureAd) throws ResourceNotFoundException {
		if (fournitureAd.getFaID() == faID) {
			FournitureAd fournitureAd1 = fournitureAdRepository.findById(faID)
					.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + faID));

			Date date = new Date();
			fournitureAd.setCreated(date);
			fournitureAdRepository.save(fournitureAd);
		}
		return fournitureAd;
	}

	@Override
	public Map<String, Boolean> deleteAd(Long faID) throws ResourceNotFoundException {
		FournitureAd fournitureAd = fournitureAdRepository.findById(faID)
				.orElseThrow(() -> new ResourceNotFoundException("FournitureAd Not Founf For this ID :: " + faID));
		fournitureAdRepository.delete(fournitureAd);
		Map<String, Boolean> response = new HashMap<>();
		response.put("FournitureAd deleted : ", Boolean.TRUE);
		return response;
	}

}