package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.enumeration.Gender;
import tn.dari.spring.enumeration.Typead;
import tn.dari.spring.enumeration.Usertype;
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
	private Date periodeOfVisibility = null;
	private Date checkInDate = null;
	private Date checkOutDate = null;
//jjjdkkdkfkffkfkfk
	//jjjjj
}
