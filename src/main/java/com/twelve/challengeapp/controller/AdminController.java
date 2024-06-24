package com.twelve.challengeapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.service.AdminService;
import com.twelve.challengeapp.util.SuccessResponseFactory;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		List<UserResponseDto> responseDtoList = adminService.getAllUsers();
		return SuccessResponseFactory.ok(responseDtoList);
	}

	@PutMapping("/users/{userId}/role")
	public ResponseEntity<?> updateUserRole(@PathVariable Long userId, @RequestBody UserRequestDto.Role role) {
		UserResponseDto userResponseDto = adminService.updateUserRole(userId, role);
		return SuccessResponseFactory.ok(userResponseDto);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		adminService.deleteUser(userId);
		return SuccessResponseFactory.noContent();
	}

	@PutMapping("/users/{userId}/promote")
	public ResponseEntity<UserResponseDto> promoteToAdmin(@PathVariable Long userId) {
		return ResponseEntity.ok(adminService.promoteToAdmin(userId));
	}

}
