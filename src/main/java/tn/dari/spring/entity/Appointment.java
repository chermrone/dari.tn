package tn.dari.spring.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Appointment implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long AppointmentId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAppdeb; /*"ddMMyyyy HH:mm:ss"*/
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAppFin; 
	private String placeApp;
	private String nameApp;
	//private String jour;
	private boolean isAccepted=false;
	//private boolean online=false;
	//private String url;
	@JsonBackReference(value = "us")
	 @ToString.Exclude
	@ManyToOne
	@JoinColumn(name="idUser", nullable=false)
	private User us;
	/*@ToString.Exclude
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<User> us = new HashSet<>();*/
	@JsonBackReference(value = "landlord")//landlord
	 @ToString.Exclude
	@ManyToOne
	@JoinColumn(name="idlandlord", nullable=false)
	private User landlord ;
		
}
