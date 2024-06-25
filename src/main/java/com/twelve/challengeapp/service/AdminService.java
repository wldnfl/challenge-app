package com.twelve.challengeapp.service;

import java.util.List;

import com.twelve.challengeapp.dto.CommentRequestDto;
import com.twelve.challengeapp.dto.CommentResponseDto;
import com.twelve.challengeapp.dto.PostRequestDto;
import com.twelve.challengeapp.dto.PostResponseDto;
import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;

public interface AdminService {

	// 유저 전체 목록 조회
	List<UserResponseDto> getAllUsers();

	// 유저 권한 수정
	UserResponseDto updateUserRole(Long userId, UserRequestDto.Role role);

	// 유저 관리자로 승격
	UserResponseDto promoteToAdmin(Long userId);

	// 유저 삭제
	void deleteUser(Long userId);

	// 모든 게시물 조회
	List<PostResponseDto> getAllPosts();

	// 게시물 수정
	PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto);

	// 게시물 삭제
	void deletePost(Long postId);

	List<CommentResponseDto> getAllComments();

	CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto);

	void deleteComment(Long commentId);

}
