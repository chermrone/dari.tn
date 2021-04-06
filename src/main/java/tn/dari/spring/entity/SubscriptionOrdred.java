package tn.dari.spring.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@JsonBackReference(value ="subscription")
	private Subscription subscription;
	
	@ManyToOne 
	@JsonBackReference(value="us")
	private User us;
}
