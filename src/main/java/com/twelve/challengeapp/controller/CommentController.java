package com.twelve.challengeapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twelve.challengeapp.dto.CommentRequestDto;
import com.twelve.challengeapp.dto.CommentResponseDto;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.service.CommentService;
import com.twelve.challengeapp.util.SuccessResponseFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/posts")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/{postId}/comments")
	public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto responseDto = commentService.createComment(postId, commentRequestDto.getContent(),
			userDetails.getUser().getId());
		return SuccessResponseFactory.ok(responseDto);
	}

	@PutMapping("/comments/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable Long commentId,
		@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto responseDto = commentService.updateComment(commentId, commentRequestDto.getContent(),
			userDetails.getUser());
		return SuccessResponseFactory.ok(responseDto);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentService.deleteComment(commentId, userDetails.getUser().getId());
		return SuccessResponseFactory.noContent();
	}

	@GetMapping("/{postId}/comments")
	public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
		List<CommentResponseDto> responseDtos = commentService.getCommentsByPostId(postId);
		return SuccessResponseFactory.ok(responseDtos);
	}
}
