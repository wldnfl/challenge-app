package com.twelve.challengeapp.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twelve.challengeapp.service.JwtServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Jwt 검증 및 인가")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtServiceImpl jwtService;

	public JwtAuthenticationFilter(JwtServiceImpl jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = jwtService.getAccessTokenFromRequest(request);
		if (accessToken != null) {
			if (jwtService.validateToken(accessToken)) {
				// Access Token이 유효한 경우 인증 정보를 설정하고 다음 필터로 진행
				log.info("Access Token 유효");
				setAuthenticationContext(accessToken);
				filterChain.doFilter(request, response);
			} else {
				// Access Token이 만료된 경우 Refresh Token을 사용하여 새로운 Access Token을 발급받음
				String refreshToken = jwtService.getRefreshTokenFromRequest(request);
				if (refreshToken != null && jwtService.validateToken(refreshToken)) {
					log.info("Access Token 만료, Refresh Token 사용");

					String username = jwtService.extractUsername(refreshToken);
					Object role = jwtService.extractRole(refreshToken);

					log.info(username + ", " + role);

					String newAccessToken = jwtService.generateAccessToken(username, role);
					setAuthenticationContext(newAccessToken);
					jwtService.setHeaderWithAccessToken(response, newAccessToken);

					filterChain.doFilter(request, response);
				} else {
					sendAuthenticationFailureResponse(response);
				}
			}
		} else {
			// Access Token이 없는 경우 다음 필터로 진행
			filterChain.doFilter(request, response);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return jwtService.isAuthorizationHeaderMissing(request);
	}

	private void setAuthenticationContext(String accessToken) {
		String username = jwtService.extractUsername(accessToken);

		// UserDetails userDetails = userDetailsService TODO : User 생기면 작업하기
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null, null);

		SecurityContextHolder.getContext().setAuthentication(authentication);
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
		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(responseBody));
	}
}
