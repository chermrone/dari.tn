package tn.dari.spring.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
import tn.dari.spring.enumeration.Typead;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryMan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long DeliveryManID;
	private String firstName;
	private String email;
	
	//@JsonBackReference
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deliveryMan")
	private Set<Delivery> deliveries;
}
