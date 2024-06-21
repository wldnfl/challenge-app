package com.twelve.challengeapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.service.UserService;
import com.twelve.challengeapp.util.SuccessResponseFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequestDto.Register requestDto) {
		userService.registerUser(requestDto);
		return SuccessResponseFactory.ok();
	}

	@GetMapping
	public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserResponseDto userInfo = userService.getUser(userDetails);
		return SuccessResponseFactory.ok(userInfo);
	}

	@DeleteMapping
	public ResponseEntity<?> withdraw(@RequestBody @Valid UserRequestDto.Withdrawal requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		userService.withdraw(requestDto, userDetails);
		return SuccessResponseFactory.ok();
	}

}
