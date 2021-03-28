package tn.dari.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("tn.dari.spring")
public class DariApplication {

	public static void main(String[] args) {
		SpringApplication.run(DariApplication.class, args);
	}

}