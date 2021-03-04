package tn.dari.spring.service;

public class AdNotFoundException extends RuntimeException {

	public AdNotFoundException(String Errormsg) {
		super(Errormsg);
	}

}
