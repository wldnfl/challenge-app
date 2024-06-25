package com.twelve.challengeapp.dto.oauth2;

import java.util.Map;

public class NaverUserResponseDto implements OAuth2ResponseDto {

	private final Map<String, Object> attribute;

	public NaverUserResponseDto(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	@Override
	public String getProvider() {

		return "naver";
	}

	@Override
	public String getProviderId() {
		Map<String, Object> response = (Map<String, Object>)attribute.get("response");
		Object providerId = response.get("id");
		return providerId != null ? providerId.toString() : null;
	}

	@Override
	public String getEmail() {
		Map<String, Object> response = (Map<String, Object>)attribute.get("response");
		Object email = response.get("email");
		return email != null ? email.toString() : null;
	}

	@Override
	public String getName() {
		Map<String, Object> response = (Map<String, Object>)attribute.get("response");
		Object name = response.get("name");
		return name != null ? name.toString() : null;
	}

}
