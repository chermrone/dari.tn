package tn.dari.spring.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CityId;
	@Column(unique=true)
String name;
String direction;
double pricemetre;
boolean famous;
double priceBuild;
  double priceRent;
  int EstimationDuration;
public City(String name, String direction) {
	super();
	this.name = name;
	this.direction = direction;
}
}
