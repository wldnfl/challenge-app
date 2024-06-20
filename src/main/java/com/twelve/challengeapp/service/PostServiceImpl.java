package com.twelve.challengeapp.service;

import com.twelve.challengeapp.dto.PostRequestDto;
import com.twelve.challengeapp.dto.PostResponseDto;
import com.twelve.challengeapp.entity.Post;
import com.twelve.challengeapp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, Long userId) {
        Post post = Post.builder()
                .userId(userId)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(int page) {
        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAll(pageRequest).map(PostResponseDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return new PostResponseDto(post);
    }

    @Override
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getUserId().equals(userId)) {
            throw new SecurityException("You are not authorized to update this post");
        }

        post.update(requestDto.getTitle(), requestDto.getContent());
        return new PostResponseDto(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getUserId().equals(userId)) {
            throw new SecurityException("You are not authorized to delete this post");
        }

        postRepository.delete(post);
    }
}
