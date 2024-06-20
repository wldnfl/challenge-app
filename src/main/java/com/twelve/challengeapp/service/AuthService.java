package com.twelve.challengeapp.service;

import com.twelve.challengeapp.dto.UserRequestDto;

public interface AuthService {

	String login(UserRequestDto.Login requestDto);

	void logout(String header);
}
