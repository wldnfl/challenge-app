package com.twelve.challengeapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessResponseFactory {

	private final static String MSG_OK = "The request has been successfully processed.";

	public static <T> ResponseEntity<SuccessResponse<T>> ok(T data) {
		SuccessResponse<T> response = new SuccessResponse<>(HttpStatus.OK.value(), MSG_OK, data);
		return ResponseEntity.ok(response);
	}

	public static ResponseEntity<SuccessResponse<Void>> ok() {
		SuccessResponse<Void> response = new SuccessResponse<>(HttpStatus.OK.value(), MSG_OK, null);
		return ResponseEntity.ok(response);
	}

}
