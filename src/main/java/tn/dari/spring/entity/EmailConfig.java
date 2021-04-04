package tn.dari.spring.entity;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter

@Component
public class EmailConfig {
	   @Value("${spring.mail.host}")
	    private String host;

	    @Value("${spring.mail.port}")
	    private int port;

	    @Value("${spring.mail.username}")
	    private String username;

	    @Value("${spring.mail.password}")
	    private String password;

}
