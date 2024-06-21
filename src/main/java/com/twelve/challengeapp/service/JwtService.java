package com.twelve.challengeapp.service;

import com.twelve.challengeapp.entity.RefreshToken;
import com.twelve.challengeapp.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtService {

	String extractUsername(String token);

	Object extractRole(String token);

	Boolean isTokenExpired(String token);

	Boolean validateToken(String token);

	Boolean isAuthorizationHeaderMissing(HttpServletRequest request);

	RefreshToken createRefreshToken(User user);

	void updateRefreshToken(RefreshToken refreshToken, User user);

	String generateAccessToken(String username, Object role);

	String getAccessTokenFromHeader(String header);

	String getAccessTokenFromRequest(HttpServletRequest request);

	String getRefreshTokenFromRequest(HttpServletRequest request);

	void setHeaderWithAccessToken(HttpServletResponse response, String accessToken);

	void setRefreshTokenAtCookie(RefreshToken refreshToken);

	void deleteRefreshTokenAtCookie();
}
