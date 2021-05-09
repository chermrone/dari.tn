package tn.dari.spring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Constraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class FournitureAd implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long faID;
	private String userName;
	@NotNull
	@NotEmpty
	private String nameFa;
	@NotNull
	private Float price;
	@NotNull
	@NotEmpty
	private String description;
	@NotNull
	@NotEmpty
	private String address;
	// @Temporal(TemporalType.DATE)
	@NotNull
	@NotEmpty
	private String created;
	@Column(columnDefinition = "boolean default 1")
	private Boolean available = true;
	@ManyToOne
	private ShoppingCart shoppingCart;
	
	@OneToMany(mappedBy = "fournitureAd",fetch=FetchType.EAGER)
	Set<LocalFile> localFile;
	
	//public Boolean getAvailable(){return this.available;}
	
}
