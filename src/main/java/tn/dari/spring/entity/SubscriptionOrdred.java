package tn.dari.spring.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionOrdred {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscriptionOrderId;
	
	@Temporal(TemporalType.DATE)
	private Date payingDate = new Date();
	
	private boolean enable=true;
	@ManyToOne
	//@JsonManagedReference(value ="subscription")
	private Subscription subscription;
	private int nbrOfWin=0;
	
	@ManyToOne
	//@JsonManagedReference(value = "us1")
	private User us;
}
