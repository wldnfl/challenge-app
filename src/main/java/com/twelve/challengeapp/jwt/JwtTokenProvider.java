package com.twelve.challengeapp.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public final class JwtTokenProvider {

	private JwtTokenProvider() {
	}

	// 토큰에서 유저 이름 추출
	public static String extractUsername(String token, SecretKey secretKey) {
		Claims claims = extractAllClaims(token, secretKey);
		return claims.getSubject();
	}

	public static Object extractRole(String token, String authorizationKey, SecretKey secretKey) {
		Claims claims = extractAllClaims(token, secretKey);
		return claims.get(authorizationKey);
	}

	// 토큰에서 만료 기간 추출
	public static Date extractExpiration(String token, SecretKey secretKey) {
		Claims claims = extractAllClaims(token, secretKey);
		return claims.getExpiration();
	}

	// 토큰 만료 확인
	public static Boolean isTokenExpired(String token, SecretKey secretKey) {
		return extractExpiration(token, secretKey).before(new Date(System.currentTimeMillis()));
	}

	// Token 생성
	public static String generateToken(String username, String authorizationKey, Object role, long expiration,
		SecretKey secretKey) {

		return Jwts.builder()
			.claim(authorizationKey, role)
			.setSubject(username)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(secretKey)
			.compact();
	}

	// 추출
	private static Claims extractAllClaims(String token, SecretKey secretKey) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}
}
