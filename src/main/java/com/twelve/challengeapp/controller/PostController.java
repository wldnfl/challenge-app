package com.twelve.challengeapp.controller;

import com.twelve.challengeapp.dto.PostRequestDto;
import com.twelve.challengeapp.dto.PostResponseDto;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.service.PostService;
import com.twelve.challengeapp.util.SuccessResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        PostResponseDto responseDto = postService.createPost(requestDto, userId);
        return SuccessResponseFactory.ok(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        PostResponseDto responseDto = postService.getPost(id);
        return SuccessResponseFactory.ok(responseDto);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        PostResponseDto responseDto = postService.updatePost(id, requestDto, userId);
        return SuccessResponseFactory.ok(responseDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        postService.deletePost(postId, userId);
        return SuccessResponseFactory.noContent();
    }
}
