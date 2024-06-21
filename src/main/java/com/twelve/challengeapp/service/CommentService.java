package com.twelve.challengeapp.service;

import com.twelve.challengeapp.entity.Comment;
import com.twelve.challengeapp.entity.User;

import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, String content, User user);
    Comment updateComment(Long commentId, String content, User user);
    void deleteComment(Long commentId, User user);
    List<Comment> getCommentsByPostId(Long postId);
}
