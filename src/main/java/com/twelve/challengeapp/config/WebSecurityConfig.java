package com.twelve.challengeapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.twelve.challengeapp.jwt.JwtAuthenticationFilter;
import com.twelve.challengeapp.jwt.UserDetailsServiceImpl;
import com.twelve.challengeapp.service.JwtServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;
	private final JwtServiceImpl jwtService;

	public WebSecurityConfig(JwtServiceImpl jwtService, UserDetailsServiceImpl userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		return new JwtAuthenticationFilter(jwtService, userDetailsService);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> csrf.disable());

		http.sessionManagement(
			(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
				.requestMatchers(HttpMethod.POST,"/api/users/**").permitAll() //POST 함수를 사용하는 /api/users/** 요청은 모두 허가
				.requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated());

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
