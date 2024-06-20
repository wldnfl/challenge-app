package com.twelve.challengeapp.service;

import com.twelve.challengeapp.dto.UserRequestDto;

public interface AuthService {

	String loginUser(UserRequestDto.Login requestDto);

}
