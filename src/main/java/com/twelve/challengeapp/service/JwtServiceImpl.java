package com.twelve.challengeapp.service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.twelve.challengeapp.config.JwtConfig;
import com.twelve.challengeapp.entity.RefreshToken;
import com.twelve.challengeapp.jwt.JwtTokenProvider;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtServiceImpl implements JwtService {

	private static final String REFRESH_TOKEN_COOKIE_NAME = "RefreshToken";

	private static SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(JwtConfig.staticSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	// 토큰 만료 확인
	@Override
	public Boolean isTokenExpired(String token) {
		return JwtTokenProvider.isTokenExpired(token, getSecretKey());
	}

	@Override
	public Boolean validateToken(String token, String username) {
		return JwtTokenProvider.validateToken(token, username, getSecretKey());
	}

	// Access Token 생성
	@Override
	public String generateAccessToken(String username, Object role) {

		return JwtTokenProvider.generateAccessToken(username, JwtConfig.staticAuthorizationKey, role,
			JwtConfig.staticRefreshTokenExpiration, getSecretKey());

	}

	// Refresh Token 생성
	@Override
	public String generateRefreshToken(Object role) {

		return JwtTokenProvider.generateRefreshToken(JwtConfig.staticAuthorizationKey, role,
			JwtConfig.staticRefreshTokenExpiration, getSecretKey());
	}

	// cookie에서 refresh token 가져오기
	@Override
	public String getRefreshTokenFromRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// Refresh Token 쿠키에 저장
	@Override
	public void setRefreshTokenAtCookie(RefreshToken refreshToken) {
		Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken.getToken());
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(JwtConfig.staticRefreshTokenExpirationSecond);
		HttpServletResponse response = ((ServletRequestAttributes)Objects.requireNonNull(
			RequestContextHolder.getRequestAttributes())).getResponse();

		if (response != null) {
			response.addCookie(cookie);
		}
	}
}
