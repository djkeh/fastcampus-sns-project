package com.fastcampus.snsproject.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;

import com.fastcampus.snsproject.configuration.filter.JwtTokenFilter;
import com.fastcampus.snsproject.service.UserService;

import lombok.*;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig extends WebSecurityConfigurerAdapter{
	
	private final UserService userService;
	@Value("${jwt.secret-key}")
	private String key;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/*/users/join", "/api/*/users/login").permitAll()
			.antMatchers("/api/**").authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class);
	}

}
