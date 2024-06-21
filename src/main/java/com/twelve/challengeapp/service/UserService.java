package com.twelve.challengeapp.service;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.jwt.UserDetailsImpl;

public interface UserService {

	void registerUser(UserRequestDto.Register requestDto);

	//회원 정보 가져오기
	UserResponseDto getUser(UserDetailsImpl userDetails);
  
	void withdraw(UserRequestDto.Withdrawal requestDto, UserDetailsImpl userDetails);

}
