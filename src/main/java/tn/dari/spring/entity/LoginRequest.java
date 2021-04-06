package tn.dari.spring.entity;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
