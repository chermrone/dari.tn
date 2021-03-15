package tn.dari.spring.exception;

public class AdNotFoundException extends RuntimeException {

	public AdNotFoundException(String Errormsg) {
		super(Errormsg);
	}

}
