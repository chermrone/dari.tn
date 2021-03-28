package tn.dari.spring.exception;

public class AppointmentNotFoundException extends RuntimeException {

	public AppointmentNotFoundException(String Errormsg) {
		super(Errormsg);
	}


}
