package com.twelve.challengeapp.service;

import com.twelve.challengeapp.entity.RefreshToken;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
	Boolean isTokenExpired(String token);

	Boolean validateToken(String token, String username);

	String generateAccessToken(String username, Object role);

	String generateRefreshToken(Object role);

	String getRefreshTokenFromRequest(HttpServletRequest request);

	void setRefreshTokenAtCookie(RefreshToken refreshToken);
}
