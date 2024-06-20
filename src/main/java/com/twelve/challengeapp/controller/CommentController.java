package com.twelve.challengeapp.controller;

import com.twelve.challengeapp.dto.CommentDto;
import com.twelve.challengeapp.entity.Comment;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.service.CommentService;
import com.twelve.challengeapp.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{post_Id}")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public SuccessResponse<Comment> createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {
        Comment comment = commentService.createComment(postId, commentDto.getContent(), user);
        return new SuccessResponse<>(comment);
    }

    @PutMapping("/comments/{commentId}")
    public SuccessResponse<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {
        Comment comment = commentService.updateComment(commentId, commentDto.getContent(), user);
        return new SuccessResponse<>(comment);
    }

    @DeleteMapping("/comments/{commentId}")
    public SuccessResponse<Void> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal User user) {
        commentService.deleteComment(commentId, user);
        return new SuccessResponse<>(null);
    }

    @GetMapping
    public SuccessResponse<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return new SuccessResponse<>(comments);
    }
}
