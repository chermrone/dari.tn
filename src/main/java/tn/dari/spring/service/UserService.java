package tn.dari.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import tn.dari.spring.entity.Claim;
import tn.dari.spring.entity.EmailConfig;
import tn.dari.spring.entity.MailHistory;
import tn.dari.spring.entity.SubscriptionOrdred;
import tn.dari.spring.entity.User;
import tn.dari.spring.enumeration.SubscriptionType;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.repository.MailHistoryRepository;
import tn.dari.spring.repository.UserRepository;
import tn.dari.spring.security.service.JwtProvider;

@Service
public class UserService implements UIuser {
	@Autowired
	UserRepository ur;

	@Autowired
	AdService adserv;

	@Autowired
	private SubscriptionOrderService sos;

	@Autowired
	private SubscriptionService ss;

	@Autowired
	EmailConfig emailCfg;

	@Autowired
	MailHistoryRepository mailHistoryRepo;

	@Override
	public List<User> GetAllUsers() {

		return ur.findAll();
	}

	@Override
	public User GetUserById(Long id) {
		return ur.findById(id).get();
	}

	@Override
	public User GetUserByFirstName(String firstname) {

		return ur.findByFirstName(firstname);
	}

	@Override
	public User GetUserByLastName(String lastname) {

		return ur.findByLastName(lastname);
	}

	@Override
	public User GetUserByUserName(String username) {

		return ur.findByUserName(username);
	}

	@Override
	public User AddUser(User u) {

		return ur.save(u);
	}

	@Override
	public User UpdateUser(User user) {

		return ur.save(user);
	}

	@Override
	public void DeleteUser(Long id) {
		ur.deleteById(id);

	}

	@Override
	public void BanUser(Long id) {
		User us = ur.findById(id).get();
		List<Claim> clmuser = new ArrayList<>();
		us.getAds().forEach(ad -> clmuser.addAll(ad.getClaims()));
		if (clmuser.size() >= 10) {
			us.setUserState(false);
			us.setBanDate(new Date());
			us.setBanNbr(us.getBanNbr() + 1);
			us.setConnected(false);
			UpdateUser(us);
		}
	}

	@Override
	public void Activate_Acount(Long id) {
		User user = ur.findById(id).get();
		user.setUserState(true);
		ur.save(user);
	}

	@Override
	public Long UserSubscribeAge(int agemin, int agemax, Long sid) {
		return ur.UserSubscribeByAge(agemin, agemax, sid);
	}
	
	@Override
	public void logout(Authentication auth) {
		User u = ur.findByUserName(auth.getName());
		u.setTimeOfLogout(new Date());
		UpdateUser(u);

	}

	@Override
	public void CalculTimeConnection(User u) {
		u.setTimeConnected(u.getTimeConnected() + TimeUnit.MILLISECONDS
				.toMinutes(Math.abs(u.getTimeOfLogout().getTime() - u.getTimeOfLogin().getTime())));
		ur.save(u);
	}

	@Override
	@Scheduled(cron = "0 53 12 * * *") // execute every mounth
	public List<User> OrderUsersByTimeOfConnection() {
		List<User> allus = GetAllUsers();
		// Order Users By Time of connection
		for (int i = 1; i < allus.size(); i++) {
			if (allus.get(i - 1).getTimeConnected() < allus.get(i).getTimeConnected()) {
				User aux = allus.get(i);
				allus.set(i, allus.get(i - 1));
				allus.set(i - 1, aux);
			}
		}
		//counter to stop premium discount if we reach 5 users
		int j = 0;
		for (int k = 0; k < allus.size(); k++) {
			//upgrade to premium if this user is not premium
			if (!allus.get(k).getRoles().contains(Usertype.PREMIUM)) {
				SubscriptionOrdred s = new SubscriptionOrdred();
				s.setSubscription(ss.GetSubscriptionBySubscriptionType(SubscriptionType.premium));
				s.setNbrOfWin(s.getNbrOfWin()+1);
				sos.AddPremiumSubscriptionorder(s, allus.get(k).getIdUser());
				j++;
				//preparing email
				JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
				mailSender.setHost(this.emailCfg.getHost());
				mailSender.setPort(this.emailCfg.getPort());
				mailSender.setUsername(this.emailCfg.getUsername());
				mailSender.setPassword(this.emailCfg.getPassword());

				// Create an email instance
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom("emna.korbi1@esprit.tn");
				mailMessage.setTo(allus.get(k).getEmail());
				mailMessage.setSubject("Premium Discount");
				mailMessage.setText("Hello M." + allus.get(k).getFirstName() + " " + allus.get(k).getLastName() + "\n"
						+ "Congratulations on winning a free month of premium.");
				System.out.println("mail created" + mailMessage);

				// Send mail
				mailSender.send(mailMessage);
				Date d = new Date(System.currentTimeMillis());
				MailHistory m = new MailHistory();
				m.setDistination(allus.get(k).getEmail());
				m.setBody(mailMessage.getText());
				m.setSendDate(d);
				m.setType("Discount Premium");
				mailHistoryRepo.save(m);
				System.out.println("mailsend" + m);
				//stop the for loop if we reach 5 winners
				if(j==5){
					return ur.saveAll(allus);
				}
			}
		}
		return ur.saveAll(allus);
	}

}
