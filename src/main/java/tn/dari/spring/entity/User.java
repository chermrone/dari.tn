package tn.dari.spring.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.enumeration.Gender;
import tn.dari.spring.enumeration.Usertype;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	private String firstName;
	private String lastName;
	private String nickName;
	private String password;
	private int age;
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;
	private int phoneNumber;
	private String email;
	private int cin;
	@Enumerated(EnumType.ORDINAL)
	private Usertype usertype;
	private boolean userState;

}
