package com.twelve.challengeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
	private String username;
	private String nickname;
	private String introduce;
	private String email;
}
