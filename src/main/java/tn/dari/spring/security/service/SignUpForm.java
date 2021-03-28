package tn.dari.spring.security.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.entity.Ad;
import tn.dari.spring.entity.Appointment;
import tn.dari.spring.entity.CreditSimulator;
import tn.dari.spring.entity.FournitureAd;
import tn.dari.spring.entity.OrderUser;
import tn.dari.spring.entity.Role;
import tn.dari.spring.entity.ShoppingCart;
import tn.dari.spring.entity.Subscription;
import tn.dari.spring.enumeration.Gender;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpForm {
 
	private Long idUser;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String password;
	
	private int age;
	
	private String urlimguser;
	
	private Gender gender;
	
	private int phoneNumber;
	
	private String email;
	
	private int cin;
	 
	private Set<String> roles = new HashSet<>();
	 
	private boolean userState;
	
	private Date creationDate;
}