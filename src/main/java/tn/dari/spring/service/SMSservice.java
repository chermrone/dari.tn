package tn.dari.spring.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import tn.dari.spring.entity.SMS;


@Component
public class SMSservice {

	    
	    private final String ACCOUNT_SID ="AC3e558711439f26ed5e198321ba337d05";

	    private final String AUTH_TOKEN = "aaa429833ca40e96ceedc40d9efac2e7";

	    private final String FROM_NUMBER = "+14066408771";
	    
	    public boolean send(String to ,String msg) {
	    	 boolean f=false;
	    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	    	SMS sms =new SMS();
	    	sms.setTo(to);
	    	sms.setMessage(msg);
	        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
	                .create();
	        System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction
	         f=true;
	       return f;
	        
	        		
	    }
	    private String getTimeStamp() {
	        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	     }

	 
}