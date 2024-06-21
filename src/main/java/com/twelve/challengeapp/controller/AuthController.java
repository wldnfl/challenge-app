package com.twelve.challengeapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.service.AuthService;
import com.twelve.challengeapp.util.SuccessResponse;
import com.twelve.challengeapp.util.SuccessResponseFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<SuccessResponse<String>> login(@RequestBody @Valid UserRequestDto.Login requestDto) {

		String token = authService.login(requestDto);
		return SuccessResponseFactory.ok(token);
	}

	@DeleteMapping("/logout")
	public ResponseEntity<SuccessResponse<Void>> logout(@RequestHeader("Authorization") String authorizationHeader) {

		authService.logout(authorizationHeader);
		return SuccessResponseFactory.ok();
	}

}
