package com.twelve.challengeapp.service;

import com.twelve.challengeapp.dto.PostRequestDto;
import com.twelve.challengeapp.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponseDto createPost(PostRequestDto requestDto, Long userId);
    Page<PostResponseDto> getPosts(Pageable pageable);
    PostResponseDto getPost(Long id);
    PostResponseDto updatePost(Long id, PostRequestDto requestDto, Long userId);
    void deletePost(Long id, Long userId);
}
