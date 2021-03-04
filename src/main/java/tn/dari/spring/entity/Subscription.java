package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
public class Subscription implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscriptionId;
	private String descriptionOffer;
	private double price;
	private boolean payed=false;
	@Temporal (TemporalType.DATE)
	private Date payingDate=null; 
	@ManyToOne
	private User us;
	public Subscription(Long subscriptionId, String descriptionOffer, double price, boolean payed, Date payingDate) {
		super();
		this.subscriptionId = subscriptionId;
		this.descriptionOffer = descriptionOffer;
		this.price = price;
		this.payed = payed;
		this.payingDate = payingDate;
	}
	public Subscription(Long subscriptionId, String descriptionOffer, double price, boolean payed, Date payingDate,
			User us) {
		super();
		this.subscriptionId = subscriptionId;
		this.descriptionOffer = descriptionOffer;
		this.price = price;
		this.payed = payed;
		this.payingDate = payingDate;
		this.us = us;
	}
	public Subscription() {
		super();
	}
	
}
