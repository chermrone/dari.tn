package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Delivery implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long DeliveryId;
	private String place;
	private boolean status;
	private double cost;
	
	@JsonBackReference(value = "orderUser")
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "deliv")
	private OrderUser orderUser;
	
	//@JsonManagedReference
	@JsonBackReference(value = "delivery")
	@ManyToOne
	DeliveryMan deliveryMan;
}
