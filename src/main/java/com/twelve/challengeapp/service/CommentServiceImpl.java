package com.twelve.challengeapp.service;

import com.twelve.challengeapp.entity.Comment;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.exception.ResourceNotFoundException;
import com.twelve.challengeapp.exception.UnauthorizedException;
import com.twelve.challengeapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment createComment(Long postId, String content, User user) {
        Comment comment = Comment.builder()
                .postId(postId)
                .content(content)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Long commentId, String content, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not authorized to update this comment");
        }

        comment.update(content);
        return comment;
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment =  commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this comment");
        }
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
