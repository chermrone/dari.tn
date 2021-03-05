package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table
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
	private Gender gender;
	private int phoneNumber;
	private String email;
	private int cin;
	@Enumerated(EnumType.ORDINAL)
	private Usertype usertype;
	private boolean userState;
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<Ad> ads;
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Ad> Favorite;
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<Subscription> subscriptions;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<OrderUser> orders;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "us")
	private ShoppingCart shoppingCart;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<Appointment> appointments;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "us")
	private Set<CreditSimulator> creditSimulators;

}
