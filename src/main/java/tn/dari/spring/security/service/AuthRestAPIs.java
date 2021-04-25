package tn.dari.spring.security.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.dari.spring.entity.Role;
import tn.dari.spring.entity.User;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.repository.UserRepository;
import tn.dari.spring.repository.RoleRepository;
import tn.dari.spring.service.UIuser;
import tn.dari.spring.service.UserService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService us;
	
	@Autowired
	UIuser userservice;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	RoleRepository rolerepository;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
		
		System.out.println("d5al lel authenticateuser");
		System.out.println(loginRequest.getUsername() +" " + loginRequest.getPassword());
		
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		System.out.println("jwt mrigal" + " "+ jwt);
		UserPrinciple userDetails1 = (UserPrinciple) authentication.getPrincipal();
		 User user=userRepository.findById(userDetails1.getId()).get(); 
		  user.setNbrOfCnx(user.getNbrOfCnx()+1);
		  user.setTimeOfLogin(new Date());
		  userRepository.save(user);
		 
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails1.getUsername(), userDetails1.getAuthorities()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUserName(signUpRequest.getUserName())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}
		
		// Creating user's account
		User user = new User(null, signUpRequest.getFirstName(),signUpRequest.getLastName(), signUpRequest.getUserName(), encoder.encode(signUpRequest.getPassword()),

				 signUpRequest.getAge(), signUpRequest.getUrlimguser(), signUpRequest.getGender(), signUpRequest.getPhoneNumber(),signUpRequest.getEmail(),signUpRequest.getCin(), true,signUpRequest.getCreationDate() , null, null, null, null, null, null);

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "ADMIN":
				Role adminRole = rolerepository.findByName(Usertype.ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				
				roles.add(adminRole);

				break;
			case "SELLER":
				Role pmRole = rolerepository.findByName(Usertype.SELLER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			case "LANDLORD":
				Role Lrole = rolerepository.findByName(Usertype.LANDLORD)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(Lrole);

				break;
			default:
				Role userRole = rolerepository.findByName(Usertype.BUYER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
	
	@PostMapping(value = "/logout")
	@PreAuthorize("hasAuthority('BUYER') or hasAuthority('ADMIN') or hasAuthority('SELLER') or hasAuthority('LANDLORD')")
	@ResponseBody	
		public ResponseEntity<?> logout(Authentication auth) {
			  us.logout(auth);
			return  ResponseEntity.ok("loggedOut");
		}
	
}