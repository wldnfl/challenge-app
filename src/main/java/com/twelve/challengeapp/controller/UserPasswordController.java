package com.twelve.challengeapp.controller;

import com.twelve.challengeapp.service.UserPasswordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserPasswordController {

	private final UserPasswordService userPasswordService;

	public UserPasswordController(UserPasswordService userPasswordService) {
		this.userPasswordService = userPasswordService;
	}
}
