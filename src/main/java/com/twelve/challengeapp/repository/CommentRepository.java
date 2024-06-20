package com.twelve.challengeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import javax.xml.stream.events.Comment;
import com.twelve.challengeapp.entity.Comment;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
