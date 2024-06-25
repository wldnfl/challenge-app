package com.twelve.challengeapp.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum UserRole {

	USER(Authority.USER),
	ADMIN(Authority.ADMIN),
	WITHDRAWAL(Authority.WITHDRAWAL),
	DELETED(Authority.DELETED);

	private final String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	public static class Authority {
		public static final String USER = "ROLE_USER";  // 사용자
		public static final String ADMIN = "ROLE_ADMIN"; // 관리자
		public static final String WITHDRAWAL = "ROLE_WITHDRAWAL";
		public static final String DELETED = "ROLE_DELETED";
	}

	public static UserRole from(String value) {
		for (UserRole status : UserRole.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		return null;
	}

	public String getValue(){
		return this.authority;
	}
}
