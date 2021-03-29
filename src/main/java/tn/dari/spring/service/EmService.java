package tn.dari.spring.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmService {
	public void sendEmail(SimpleMailMessage email);
}
