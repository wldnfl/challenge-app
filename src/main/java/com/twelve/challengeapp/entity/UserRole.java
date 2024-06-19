package com.twelve.challengeapp.entity;

import lombok.Getter;

@Getter
public enum UserRole {

	USER(Authority.USER),
	ADMIN(Authority.ADMIN);

	private final String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	public static class Authority {
		public static final String USER = "ROLE_USER";  // 사용자
		public static final String ADMIN = "ROLE_ADMIN"; // 관리자
	}
}
