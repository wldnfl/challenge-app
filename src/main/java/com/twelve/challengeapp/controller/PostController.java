package com.twelve.challengeapp.controller;

import com.twelve.challengeapp.dto.PostRequestDto;
import com.twelve.challengeapp.dto.PostResponseDto;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.service.PostService;
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
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        PostResponseDto responseDto = postService.createPost(requestDto, userId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        PostResponseDto responseDto = postService.updatePost(id, requestDto, userId);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUserId();
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }
}
