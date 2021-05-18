package tn.dari.spring.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@JsonPropertyOrder({"shoppingCartId", "dateadded","quantity", "fournitureAds" })
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ShoppingCartId;
	private int Quantity;
	@Temporal(TemporalType.DATE)
	private Date dateadded;

	private String address;
	
	@OneToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	private Set<FournitureAd> fournitureAds;
	
	@JsonBackReference
	@ManyToOne
	private User us;
	
	
	@OneToOne
	private OrderUser orderUser;

}
