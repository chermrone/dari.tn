package tn.dari.spring.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;



import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class EmailService {
	public boolean sendMail(String from, String to, String subject, String msg) {  
       /* //creating message  
        SimpleMailMessage message = new SimpleMailMessage();  
        message.setFrom(from);  
        message.setTo(to);  
        message.setSubject(subject);  
        message.setText(msg);  
        //sending message  
        mailSender.send(message);     
    } */ 
		boolean f=false;
		String host="smtp.gmail.com"  ;
	
	
	Properties properties =System.getProperties();
	System.out.println("Properties"+properties);
	
	properties.put("mail.smtp.host", host);
	properties.put("mail.smtp.port","465");
	properties.put("mail.smtp.ssl.enable", "true");
	properties.put("mail.smtp.auth", "true");
	
	Session session=Session.getInstance(properties ,new Authenticator(){
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication("ahmed.ahmed10455@gmail.com", "*********");
		}
	});
			

session.setDebug(true);


MimeMessage m =new MimeMessage(session);
 try {
	 m.setFrom(from);
	 m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	 m.setSubject(subject);
	 m.setText(msg);
	 
	 Transport.send(m);
	 System.out.println("sent success");
	 f=true;
 }catch(Exception e){
	 e.printStackTrace();
	 
 }
return f;

	}

}
