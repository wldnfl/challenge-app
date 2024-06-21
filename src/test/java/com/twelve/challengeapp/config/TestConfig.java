package com.twelve.challengeapp.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.twelve.challengeapp.jwt.UserDetailsServiceImpl;
import com.twelve.challengeapp.service.JwtServiceImpl;

@TestConfiguration
public class TestConfig {

	@Bean
	@Primary
	public PasswordEncoder testPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Primary
	public JwtServiceImpl testJwtService() {
		return Mockito.mock(JwtServiceImpl.class);
	}

	@Bean
	@Primary
	public UserDetailsServiceImpl testUserDetailsService() {
		return Mockito.mock(UserDetailsServiceImpl.class);
	}

	@Bean
	@Primary
	public AuthenticationManager testAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}


	// JwtConfig 관련 테스트 설정
	@Bean
	@Primary
	public JwtConfig testJwtConfig() {
		JwtConfig jwtConfig = new JwtConfig();

		JwtConfig.staticSecretKey = "testSecretKey";
		JwtConfig.staticAuthorizationKey = "testAuthorizationKey";
		JwtConfig.staticHeader = "Authorization";
		JwtConfig.staticTokenPrefix = "Bearer ";
		JwtConfig.staticAccessTokenExpiration = 1800000L; // 30분
		JwtConfig.staticRefreshTokenExpiration = 1209600000L; // 2주
		JwtConfig.staticRefreshTokenExpirationSecond = 1209600;
		return jwtConfig;
	}

	@Bean
	@Primary
	public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf((csrf) -> csrf.disable())
			.sessionManagement((sessionManagement) ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests((authorizeHttpRequests) ->
				authorizeHttpRequests.anyRequest().permitAll());
		return http.build();
	}
}
