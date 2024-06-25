package com.twelve.challengeapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twelve.challengeapp.dto.CommentRequestDto;
import com.twelve.challengeapp.dto.PostRequestDto;
import com.twelve.challengeapp.dto.UserRequestDto;
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
		return SuccessResponseFactory.ok(adminService.getAllUsers());
	}

	@PutMapping("/users/{userId}/role")
	public ResponseEntity<?> updateUserRole(@PathVariable Long userId, @RequestBody UserRequestDto.Role role) {
		return SuccessResponseFactory.ok(adminService.updateUserRole(userId, role));
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		adminService.deleteUser(userId);
		return SuccessResponseFactory.noContent();
	}

	@PutMapping("/users/{userId}/promote")
	public ResponseEntity<?> promoteToAdmin(@PathVariable Long userId) {
		return SuccessResponseFactory.ok(adminService.promoteToAdmin(userId));
	}

	@GetMapping("/posts")
	public ResponseEntity<?> getAllPosts() {
		return SuccessResponseFactory.ok(adminService.getAllPosts());
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
		return SuccessResponseFactory.ok(adminService.updatePost(postId, postRequestDto));
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
		adminService.deletePost(postId);
		return SuccessResponseFactory.noContent();
	}

	@GetMapping("/comments")
	public ResponseEntity<?> getAllComment() {
		return SuccessResponseFactory.ok(adminService.getAllComments());
	}

	@PutMapping("/comments/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
		return SuccessResponseFactory.ok(adminService.updateComment(commentId, requestDto));
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
		adminService.deleteComment(commentId);
		return SuccessResponseFactory.noContent();
	}

}
