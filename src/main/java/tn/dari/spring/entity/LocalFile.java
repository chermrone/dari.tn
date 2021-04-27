package tn.dari.spring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileID;
	private String path;
	@JsonIgnore
	@ManyToOne
	FournitureAd fournitureAd;

}
