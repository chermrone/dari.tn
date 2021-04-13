package tn.dari.spring.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum Usertype implements GrantedAuthority {
	ADMIN, BUYER, SELLER, LANDLORD;
	public String getAuthority() {
	    return name();
	  }
}