package com.twelve.challengeapp.controller;

import com.twelve.challengeapp.dto.CommentRequestDto;
import com.twelve.challengeapp.dto.CommentResponseDto;
import com.twelve.challengeapp.entity.Comment;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.service.CommentService;
import com.twelve.challengeapp.util.SuccessResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/posts/{postId}")
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Comment comment = commentService.createComment(postId, commentRequestDto.getContent(), userDetails.getUser());
        CommentResponseDto responseDto = new CommentResponseDto(comment.getId(), comment.getContent(), comment.getUser().getUsername(), comment.getCreatedAt(), comment.getUpdatedAt());
        return SuccessResponseFactory.ok(responseDto);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Comment comment = commentService.updateComment(commentId, commentRequestDto.getContent(), userDetails.getUser());
        CommentResponseDto responseDto = new CommentResponseDto(comment.getId(), comment.getContent(), comment.getUser().getUsername(), comment.getCreatedAt(), comment.getUpdatedAt());
        return SuccessResponseFactory.ok(responseDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return SuccessResponseFactory.noContent();
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<CommentResponseDto> responseDtos = comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getContent(), comment.getUser().getUsername(), comment.getCreatedAt(), comment.getUpdatedAt()))
                .collect(Collectors.toList());
        return SuccessResponseFactory.ok(responseDtos);
    }
}
