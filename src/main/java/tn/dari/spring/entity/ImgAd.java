package tn.dari.spring.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter

@NoArgsConstructor
@ToString
public class ImgAd implements Serializable {



//jjjjj

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
	
@Lob
@Column(name = "picByte")
	private byte[] picByte;
 @JsonBackReference
@ManyToOne private Ad ad;
	


	public ImgAd(String name, String type, byte[] picByte) {
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}



	public ImgAd(String name, String type, byte[] picByte, Ad ad) {
		super();
		this.name = name;
		this.type = type;
		this.picByte = picByte;
		this.ad = ad;
	}	
	
}
