package tn.dari.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tn.dari.spring.jwt.JwtRequestFilter;
import tn.dari.spring.service.MyUserService;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserService myuserDetailsService;

	@Autowired
	private JwtRequestFilter JwtRequestFilter;

	// Configure va appeler la methode loaduserbyusername de la classe
	// MyUserService pour qu'elle trouve le user ayant le username passé en
	// parametre
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myuserDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/dari/Users/Authenticate").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// Stateless
																													// to
																													// say
																													// to
																													// spring
																													// security
																													// to
																													// do
																													// not
																													// manage
																													// sessions
		http.addFilterBefore(JwtRequestFilter, UsernamePasswordAuthenticationFilter.class);// make
																							// sure
																							// jwtrequestfilter
																							// is
																							// called
																							// befaure
																							// usernamepasswordauthenticationfilter
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordencoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	

}
