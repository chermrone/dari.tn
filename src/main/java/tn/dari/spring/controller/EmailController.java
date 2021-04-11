package tn.dari.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.EmailRequest;
import tn.dari.spring.service.EmailService;

@RestController
public class EmailController {
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/sendemail",method = RequestMethod.POST)
	public ResponseEntity<?> sendMail(@RequestBody EmailRequest request){
		//
		System.out.println(request);
		boolean result=this.emailService.sendMail(request.getFrom(),request.getTo(),request.getSubject(),request.getMsg());
		if(result)
		{
		return ResponseEntity.ok("Done email send");
		}else
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email not send");
		}
		
	}

}
