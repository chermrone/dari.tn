package tn.dari.spring.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import tn.dari.spring.service.MyUserService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private MyUserService myuserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader= request.getHeader("Authorization");
		// TODO Auto-generated method stub
		
		String username=null;
		String jwt=null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
			jwt = authorizationHeader.substring(7);
			username=jwtUtil.extractUsername(jwt);
		}
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
			UserDetails userdetails= this.myuserDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userdetails)){
				UsernamePasswordAuthenticationToken usernamepasswordauthenticationtoken= new  UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
				usernamepasswordauthenticationtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamepasswordauthenticationtoken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
