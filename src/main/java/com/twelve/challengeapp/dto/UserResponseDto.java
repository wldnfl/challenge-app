package com.twelve.challengeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
	private String username;
	private String nickname;
	private String introduce;
	private String email;
}
