package tn.dari.spring.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
	private final String jwt;
	public String getJwt(){
		return jwt;
	}
}
