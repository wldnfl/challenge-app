package com.twelve.challengeapp.dto.oauth2;

import java.util.Map;

public class GoogleUserResponseDto implements OAuth2ResponseDto {

	private final Map<String, Object> attribute;

	public GoogleUserResponseDto(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	@Override
	public String getProvider() {
		return "google";
	}

	@Override
	public String getProviderId() {
		Object providerId = attribute.get("sub");
		return providerId != null ? providerId.toString() : null;
	}

	@Override
	public String getEmail() {
		Object email = attribute.get("email");
		return email != null ? email.toString() : null;
	}

	@Override
	public String getName() {
		Object name = attribute.get("name");
		return name != null ? name.toString() : null;
	}
}
