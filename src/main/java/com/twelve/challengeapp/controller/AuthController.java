package com.twelve.challengeapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.service.AuthServiceImpl;
import com.twelve.challengeapp.util.SuccessResponse;
import com.twelve.challengeapp.util.SuccessResponseFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class AuthController {

	private final AuthServiceImpl authService;

	public AuthController(AuthServiceImpl authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<SuccessResponse<String>> loginUser(@RequestBody @Valid UserRequestDto.Login requestDto) {
		String token = authService.loginUser(requestDto);
		return SuccessResponseFactory.ok(token);
	}

}
