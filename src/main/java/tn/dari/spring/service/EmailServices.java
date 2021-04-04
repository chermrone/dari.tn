package tn.dari.spring.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class EmailServices {
	private JavaMailSender javaMailSender;
    public EmailServices(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sendMail(String toEmail, String subject, String message) throws Exception {

    	/*SimpleMailMessage  mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("wazkasmi@gmail.com");
        javaMailSender.send(mailMessage);*/
    	MimeMessage mes = javaMailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mes);

    	helper.setSubject(subject);
    	helper.setFrom("emna.korbi1@esprit.tn");
    	helper.setTo(toEmail);
    	boolean html = true;
    	helper.setText(message, html);

    	javaMailSender.send(mes);
    }
}
