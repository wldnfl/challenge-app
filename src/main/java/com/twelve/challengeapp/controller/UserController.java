package com.twelve.challengeapp.controller;

import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.util.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.service.UserServiceImpl;
import com.twelve.challengeapp.util.SuccessResponseFactory;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserServiceImpl userService;

	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequestDto.Register requestDto) {
		userService.registerUser(requestDto);
		return SuccessResponseFactory.ok();
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponse<Void>> withDrawl(@RequestBody @Valid UserRequestDto.Login requestDto,
														   @AuthenticationPrincipal UserDetailsImpl userDetails) {

		userService.withDrawl(requestDto, userDetails);
		return SuccessResponseFactory.ok();
	}

}
