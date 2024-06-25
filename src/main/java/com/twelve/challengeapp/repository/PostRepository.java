package com.twelve.challengeapp.repository;

import com.twelve.challengeapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
