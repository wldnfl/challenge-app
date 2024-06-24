package com.twelve.challengeapp.service;

import java.util.List;

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

}
