package com.twelve.challengeapp.dto.oauth2;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuth2UserInfo {
	private String role;
	private String name;
	private String username;
	private String email;
}
