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
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import com.twelve.challengeapp.jwt.JwtAuthenticationFilter;
import com.twelve.challengeapp.jwt.UserDetailsServiceImpl;
import com.twelve.challengeapp.service.CustomOAuth2UserServiceImpl;
import com.twelve.challengeapp.service.JwtServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;
	private final JwtServiceImpl jwtService;
	private final CustomOAuth2UserServiceImpl customOAuth2UserService;

	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JwtServiceImpl jwtService,
		CustomOAuth2UserServiceImpl customOAuth2UserService) {
		
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.customOAuth2UserService = customOAuth2UserService;
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

		//csrf disable
		http
			.csrf((auth) -> auth.disable());

		//From 로그인 방식 disable
		http
			.formLogin((auth) -> auth.disable());

		//HTTP Basic 인증 방식 disable
		http
			.httpBasic((auth) -> auth.disable());

		//JWTFilter 추가
		http
			.addFilterAfter(new JwtAuthenticationFilter(jwtService, userDetailsService),
				OAuth2LoginAuthenticationFilter.class);

		//oauth2
		http
			.oauth2Login((oauth2) -> oauth2
				.userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
					.userService(customOAuth2UserService))
			);

		//세션 설정 : STATELESS
		http
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
        .requestMatchers(HttpMethod.POST,"/api/users/**").permitAll() //POST 함수를 사용하는 /api/users/** 요청은 모두 허가
				.requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
				.requestMatchers(HttpMethod.GET,"/api/posts/**").permitAll()
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated());

		return http.build();
	}
}
