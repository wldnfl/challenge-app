package com.twelve.challengeapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.twelve.challengeapp.dto.PostRequestDto;
import com.twelve.challengeapp.dto.PostResponseDto;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.service.PostService;
import com.twelve.challengeapp.util.SuccessResponseFactory;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	// 게시글 등록
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUserId();
		PostResponseDto responseDto = postService.createPost(requestDto, userId);
		return SuccessResponseFactory.ok(responseDto);
	}

	// 선택 게시글 조회
	@GetMapping("/{postId}")
	public ResponseEntity<?> getPost(@PathVariable Long postId) {
		PostResponseDto responseDto = postService.getPost(postId);
		return SuccessResponseFactory.ok(responseDto);
	}

	// 전체 게시글 조회
	@GetMapping
	public ResponseEntity<?> getPosts(
		@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<PostResponseDto> posts = postService.getPosts(pageable);
		return SuccessResponseFactory.ok(posts);
	}

	// 선택 게시글 수정
	@PutMapping("/{postId}")
	public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUserId();
		PostResponseDto responseDto = postService.updatePost(postId, requestDto, userId);
		return SuccessResponseFactory.ok(responseDto);
	}

	// 선택 게시글 삭제
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long postId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUserId();
		postService.deletePost(postId, userId);
		return SuccessResponseFactory.noContent();
	}
}
