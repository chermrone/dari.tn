package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;

import javax.persistence.Column;

import javax.persistence.ElementCollection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.enumeration.Gender;

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
	
	
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private int phoneNumber;
	
	private String email;
	
	private int cin;
	
	@JsonIgnore
    private String resetToken;
	
	@JsonIgnore
	private boolean isConnected;

	
	@ToString.Exclude
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	private boolean userState;
	
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@Temporal(TemporalType.DATE)
	private Date banDate=null;
	
	private int banNbr=0;
	
	@JsonManagedReference
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us", fetch = FetchType.EAGER)
	private Set<Ad> ads;
	
	@ToString.Exclude
	@ElementCollection(targetClass=Long.class)
	private Set<Long> Favorite;
	
	@JsonManagedReference(value="us")
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us", fetch = FetchType.EAGER)
	private Set<SubscriptionOrdred> subscriptions;
	
	//seiiifffff
	
	@JsonManagedReference
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<OrderUser> orders;
	
	//@JsonManagedReference
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL)
	private Set<FournitureAd> fournitureAds;

	//seiiifffff
     @JsonManagedReference(value = "us")
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us",fetch = FetchType.EAGER)
	private Set<Appointment> appointments;
	@JsonManagedReference(value = "landlord")
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "landlord", fetch = FetchType.EAGER)
	private Set<Appointment> appointment;
	
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us", fetch = FetchType.EAGER)
	private Set<CreditSimulator> creditSimulators;
	
	private int nbrOfCnx=0;
	private long timeConnected=0;
	private Date timeOfLogin;
	private Date timeOfLogout;

	public User(Long idUser, String firstName, String lastName, String userName, String password, int age,
			String urlimguser, Gender gender, int phoneNumber, String email, int cin, boolean userState,
			Date creationDate, Set<Ad> ads, Set<Long> favorite, Set<SubscriptionOrdred> subscriptions, Set<OrderUser> orders,
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
		this.appointments = appointments;
		this.creditSimulators = creditSimulators;
	}
	
	@JsonManagedReference
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us",fetch = FetchType.EAGER)
	private Set<ImgUser> imguser;
}
