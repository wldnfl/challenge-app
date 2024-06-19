package com.twelve.challengeapp.service;

import com.twelve.challengeapp.dto.UserRequestDto;

public interface UserService {

	void registerUser(UserRequestDto.Register requestDto);

}
