package com.twelve.challengeapp.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twelve.challengeapp.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Jwt 검증 및 인가")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		objectMapper = new ObjectMapper();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			String accessToken = jwtService.getAccessTokenFromRequest(request);
			if (accessToken != null) {
				if (jwtService.validateToken(accessToken)) {
					log.info("Access Token 유효");
					setAuthenticationContext(accessToken);
				} else {
					handleExpiredAccessToken(request, response);
				}
			}
		} catch (Exception e) {
			log.error("JWT error", e);
			sendAuthenticationFailureResponse(response);
			return;
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return jwtService.isAuthorizationHeaderMissing(request);
	}

	private void handleExpiredAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String refreshToken = jwtService.getRefreshTokenFromRequest(request);
		if (refreshToken != null && jwtService.validateToken(refreshToken)) {
			log.info("Access Token 만료, Refresh Token 사용");
			String username = jwtService.extractUsername(refreshToken);
			Object role = jwtService.extractRole(refreshToken);
			String newAccessToken = jwtService.generateAccessToken(username, role);
			setAuthenticationContext(newAccessToken);
			jwtService.setHeaderWithAccessToken(response, newAccessToken);
		} else {
			sendAuthenticationFailureResponse(response);
		}
	}

	private void sendAuthenticationFailureResponse(HttpServletResponse response) throws IOException {
		// 응답 상태 코드 설정
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		// 응답 컨텐츠 타입 설정
		response.setContentType("application/json");

		// 응답 본문 생성
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		responseBody.put("error", "Unauthorized");
		responseBody.put("message", "Authentication failed");

		// 응답 본문을 JSON 형식으로 변환하여 출력
		response.getWriter().write(objectMapper.writeValueAsString(responseBody));
	}

	private void setAuthenticationContext(String accessToken) {
		String username = jwtService.extractUsername(accessToken);

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
			userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}