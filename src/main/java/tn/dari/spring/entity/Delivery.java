package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder({"deliveryId", "place","status", "cost" })
public class Delivery implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long DeliveryId;
	private String place;
	private boolean status;
	private double cost;
	
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	private Date date;
	
	@JsonBackReference(value = "orderUser")
	@OneToOne
	private OrderUser orderUser;
	
	
	@JsonBackReference(value = "delivery")
	@ManyToOne
	DeliveryMan deliveryMan;
}
