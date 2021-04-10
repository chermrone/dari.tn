package tn.dari.spring.controller;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.EmailConfig;
import tn.dari.spring.entity.MailHistory;
import tn.dari.spring.entity.ResetPassword;
import tn.dari.spring.entity.User;
import tn.dari.spring.repository.MailHistoryRepository;
import tn.dari.spring.repository.UserRepository;
import tn.dari.spring.security.service.LoginForm;

@RestController
public class ResetPasswordController {
	@Autowired
	EmailConfig emailCfg;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordencoder;

	@Autowired
	MailHistoryRepository mailHistoryRepo;

	
	
	@PostMapping("/forgot")
	public String processForgotPasswordForm(@RequestBody LoginForm loginrequest, HttpServletRequest request) {
		
		User user = userRepository.findByUserName(loginrequest.getUsername());
		System.out.println("d5al lel findName"+user);
		user.setResetToken(UUID.randomUUID().toString());
		userRepository.save(user);
		String appUrl = request.getScheme() + "://" + request.getServerName();

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.emailCfg.getHost());
		mailSender.setPort(this.emailCfg.getPort());
		mailSender.setUsername(this.emailCfg.getUsername());
		mailSender.setPassword(this.emailCfg.getPassword());
		
		// Create an email instance
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("emna.korbi1@esprit.tn");
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Reset  Password dari");
		mailMessage.setText(
				"To reset your password, use this code \n" + user.getResetToken());
		System.out.println("mail created"+mailMessage);
		// Send mail
		mailSender.send(mailMessage);
		Date d = new Date(System.currentTimeMillis());
		MailHistory m = new MailHistory();
		m.setDistination(user.getEmail());
		m.setBody("To reset your password, use this code \n" + user.getResetToken());
		m.setSendDate(d);
		m.setType("resetPassword");
		mailHistoryRepo.save(m);
		System.out.println("mailsend"+m);
		return user.getResetToken();
	}

	@PostMapping("/reset")
	public String setNewPassword(@Valid @RequestBody ResetPassword resetpass) {

		Optional<User> user = userRepository.findByResetToken(resetpass.getToken());

		User resetUser = user.get();

		// Set new password
		resetUser.setPassword(passwordencoder.encode(resetpass.getPassword()));
		
		// Set the reset token to null so it cannot be used again
		resetUser.setResetToken(null);

		// Save user
		userRepository.save(resetUser);
		return "password updated";
	}
}
