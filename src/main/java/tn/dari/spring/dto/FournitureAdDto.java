package tn.dari.spring.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FournitureAdDto {

	
	private Long faID;
	private String userName;
	private String nameFa;
	private Float price;
	private String description;
	private String address;
	private String created;
	private Boolean available;
}
