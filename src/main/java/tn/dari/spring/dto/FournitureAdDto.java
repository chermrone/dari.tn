package tn.dari.spring.dto;


import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.dari.spring.entity.LocalFile;

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
	private Set<LocalFile> localFile;
}
