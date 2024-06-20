package com.twelve.challengeapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
