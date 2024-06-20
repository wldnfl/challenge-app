package com.twelve.challengeapp.controller;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.service.AuthServiceImpl;
import com.twelve.challengeapp.util.SuccessResponse;
import com.twelve.challengeapp.util.SuccessResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthServiceImpl authService;

	public AuthController(AuthServiceImpl authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<SuccessResponse<String>> login(@RequestBody @Valid UserRequestDto.Login requestDto) {

		String token = authService.login(requestDto);
		return SuccessResponseFactory.ok(token);
	}

	@DeleteMapping("/logout")
	public ResponseEntity<SuccessResponse<Void>> logout(
		@RequestHeader("Authorization") String authorizationHeader) {

		authService.logout(authorizationHeader);
		return SuccessResponseFactory.ok();
	}

}
