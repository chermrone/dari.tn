package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.enumeration.SubscriptionType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Subscription implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscriptionId;
	private String title;
	private String descriptionOffer;
	private double price;
	private boolean payed = false;
	@Enumerated(EnumType.STRING)
	private SubscriptionType subscriptiontype;
	@Temporal(TemporalType.DATE)
	private Date payingDate = null;
<<<<<<< Updated upstream
=======
	private boolean validity;
>>>>>>> Stashed changes
	@JsonBackReference
	@ManyToMany
	private Set<User> us;
	public Subscription(Long subscriptionId, String descriptionOffer, double price, boolean payed, Date payingDate) {
		super();
		this.subscriptionId = subscriptionId;
		this.descriptionOffer = descriptionOffer;
		this.price = price;
		this.payed = payed;
		this.payingDate = payingDate;
	}
	
}
