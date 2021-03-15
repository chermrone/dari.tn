package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.enumeration.Gender;
import tn.dari.spring.enumeration.Usertype;
import tn.dari.spring.entity.Ad;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private int age;

	@Enumerated(EnumType.ORDINAL)

	@Column(name = "name")
	private String Imgname;

	@Column(name = "type")
	private String type;

    
@Lob
@Column(name = "imgByte")
	private byte[] imgByte;

	@Enumerated(EnumType.STRING)

	private String urlimguser;
	@Enumerated(EnumType.STRING)

	private Gender gender;
	private int phoneNumber;
	private String email;
	private int cin;
	 @ToString.Exclude
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	private boolean userState;

	@Temporal(TemporalType.DATE)
	private Date creationDate;
	@JsonManagedReference
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<Ad> ads;
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Ad> Favorite;
	@JsonManagedReference
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<Subscription> subscriptions;
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<OrderUser> orders;
	 @ToString.Exclude
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "us")
	private ShoppingCart shoppingCart;
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<Appointment> appointments;
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<CreditSimulator> creditSimulators;

	public User(Long idUser, String firstName, String lastName, String userName, String password, int age,
			String urlimguser, Gender gender, int phoneNumber, String email, int cin, boolean userState,
			Date creationDate, Set<Ad> ads, Set<Ad> favorite, Set<Subscription> subscriptions, Set<OrderUser> orders,
			ShoppingCart shoppingCart, Set<Appointment> appointments, Set<CreditSimulator> creditSimulators) {
		super();
		this.idUser = idUser;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.age = age;
		
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.cin = cin;
		this.userState = userState;
		this.creationDate = creationDate;
		this.ads = ads;
		Favorite = favorite;
		this.subscriptions = subscriptions;
		this.orders = orders;
		this.shoppingCart = shoppingCart;
		this.appointments = appointments;
		this.creditSimulators = creditSimulators;
	}
	public User(String originalFilename, String contentType, byte[] compressBytes, User usJson) {
		// TODO Auto-generated constructor stub
	}
	


}
