package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	private String descriptionOffer;
	private double price;
	@Enumerated(EnumType.STRING)
	private SubscriptionType subscriptiontype;
	private boolean validity=true;
	private long duration;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "subscription", fetch = FetchType.EAGER)
	@JsonBackReference//(value ="subscription")
	@ToString.Exclude
	private Set<SubscriptionOrdred> subord;
	public Subscription(Long subscriptionId, String descriptionOffer, double price) {
		super();
		this.subscriptionId = subscriptionId;
		this.descriptionOffer = descriptionOffer;
		this.price = price;
	}
	
}
