package com.twelve.challengeapp.exception;

public class TokenNotFoundException extends RuntimeException {
	public TokenNotFoundException(String message) {
		super(message);
	}
}
