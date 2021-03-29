package tn.dari.spring.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Claim;
import tn.dari.spring.entity.User;

import tn.dari.spring.exception.UserNotFoundException;
import tn.dari.spring.repository.UserRepository;
import tn.dari.spring.security.service.SignUpForm;

@Service
public class UserService implements UIuser {
	@Autowired
	UserRepository ur;

	@Autowired
	AdService adserv;
	
	
@Autowired
PasswordEncoder encoder;


	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
	@Override
	public List<User> GetAllUsers() {

		return ur.findAll();
	}

	@Override
	public User GetUserById(Long id) {
		return ur.findById(id).get();
		// return ur.findById(id).orElseThrow(() -> new
		// UserNotFoundException("user by id= " + id + " was not found"));
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

		return ur.findByUserName(username);// .get();
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
		List<Ad> ad = adserv.getAll();
		List<Ad> aduser = new ArrayList<>();
		for (Ad ad2 : ad) {
			if (ad2.getUs().getIdUser() == id) {
				aduser.add(ad2);
			}
		}
		List<Claim> clmuser = new ArrayList<>();
		for (Ad ad3 : aduser) {
			clmuser.addAll(ad3.getClaims());
		}
		if (clmuser.size() >= 10) {
			User us = GetUserById(id);
			us.setUserState(false);
			UpdateUser(us);
		}
	}
	
	public String forgotPassword(String email) {

		Optional<User> userOptional = Optional
				.ofNullable(ur.findByEmail(email));

		if (!userOptional.isPresent()) {
			return "Invalid email id.";
		}

		User user = userOptional.get();
		
		user.setToken(generateToken());
		user.setTokenCreationDate(LocalDateTime.now());

		user = ur.save(user);

		return user.getToken();
	}

	public String resetPassword(String token, String password){
		Optional<User> userOptional = Optional
				.ofNullable(ur.findByToken(token));

		if (!userOptional.isPresent()) {
			return "Invalid token.";
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return "Token expired.";

		}

		User user = userOptional.get();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
		user.setToken(null);
		user.setTokenCreationDate(null);

		ur.save(user);

		return "Your password successfully updated.";
	}

	/**
	 * Generate unique token. You may add multiple parameters to create a strong
	 * token.
	 * 
	 * @return unique token
	 */
	private String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString())
				.append(UUID.randomUUID().toString()).toString();
	}

	/**
	 * Check whether the created token expired or not.
	 * 
	 * @param tokenCreationDate
	 * @return true or false
	 */
	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}

	
	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User customer = ur.findByEmail(email);
        if (customer != null) {
            customer.setToken(token);
          ur.save(customer);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }
     
    public User getByResetPasswordToken(String token) {
        return ur.findByToken(token);
    }
     
    public void updatePassword(User customer, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedPassword);
         
        customer.setToken(null);
        ur.save(customer);
    }
}
