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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.enumeration.TypeBatiment;
import tn.dari.spring.enumeration.Typead;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ad implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adId;
	private String titleAd;
	@Enumerated(EnumType.STRING)
	private TypeBatiment type;
	private String Description;
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date creationDate;
	private boolean sell;
	@Temporal(TemporalType.DATE)
	private Date BuyingDate = null;
	private boolean visibility;
	@Temporal(TemporalType.DATE)
	private Date periodeOfVisibility = null;// when would he want to rent his house
	private int numbreOfRooms;
	private double price;
	private String city;
	private double builda;
	private double area;
	@Enumerated(EnumType.STRING)
	private Typead typead;
	private int numberOfBathrooms;
	@Temporal(TemporalType.DATE)
	private Date checkInDate = null;
	@Temporal(TemporalType.DATE)
	private Date checkOutDate = null;
	@JsonManagedReference
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ad")
	private Set<FilesAd> imgads;
	@JsonBackReference
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ad")
	private Set<Claim> claims;
	@JsonManagedReference
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ad")
	private Set<Wishlist> wishlists;
	//@JsonBackReference
	@ManyToOne
	private User us;
	@JsonManagedReference
	 @ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ad")
	private Set<Review> rev;
private int numbeOfVisites ;
private double estimationPeriod;
//will add comment recordinf to number of visites and faavorites
private String feedback;

}