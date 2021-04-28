package tn.dari.spring.security.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UrlPathHelper;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthTokenFilter authenticationJwtTokenFilter() {
		System.out.println("d5al lel jwtauthfilter bean");
		return new JwtAuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		System.out.println("d5al lel authentication manager builder");
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		System.out.println("d5al lel authentication manager bean");
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("d5al lel configure");
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/api/auth/**").permitAll()
		.antMatchers("/dari/subscriptions/all").permitAll()
		.antMatchers("/dari/subscriptions/find/**").permitAll()
		.antMatchers("/dari/ads/all").permitAll()
		.antMatchers("/dari/ads/ad/**").permitAll()
		.antMatchers("/dari/imgads/all").permitAll()
		.antMatchers("/forgot").permitAll()
		.antMatchers("/reset").permitAll()
		.antMatchers("/paypal/complete/payment").permitAll()
		.antMatchers("/api/payment").permitAll()
		.antMatchers("/dari/Users/user/lastad").permitAll()
		.antMatchers("/dari/imgusers/upload/**").permitAll()
		.antMatchers("/dari/imgusers/all").permitAll()
		.anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().logout().logoutSuccessUrl("/api/auth/signin");

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}