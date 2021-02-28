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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.enumeration.Typead;
@Entity
@Table
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
	private String Description;
	@Temporal (TemporalType.DATE)
	private Date creationDate;
	private boolean sell;
	private boolean visibility;
	private int numbreOfRooms;
	private double price;
	private String city;
	@Enumerated(EnumType.ORDINAL)
	private Typead typead;
	private int numberOfBathrooms;
	private double area;
	@Temporal (TemporalType.DATE)
	private Date periodeOfVisibility = null;
	@Temporal (TemporalType.DATE)
	private Date checkInDate = null;
	@Temporal (TemporalType.DATE)
	private Date checkOutDate = null;
	@OneToMany(cascade = CascadeType.ALL)
	private Set<ImgAd> imgads;
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Claim> claims;
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Wishlist> wishlists;
}
