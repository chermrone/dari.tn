package tn.dari.spring.Notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.dari.spring.entity.Ad;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Notification {
	   private String title;
	    private Ad ad;
	 
}
