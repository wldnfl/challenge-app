package com.twelve.challengeapp.dto.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

	private final OAuth2UserInfo userInfo;

	public CustomOAuth2User(OAuth2UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<>();
		if (userInfo.getEmail() != null) {
			attributes.put("email", userInfo.getEmail());
		}
		if (userInfo.getName() != null) {
			attributes.put("name", userInfo.getName());
		}
		// 다른 사용자 속성들도 필요에 따라 추가할 수 있음
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		if (userInfo.getRole() != null) {
			collection.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return userInfo.getRole();
				}
			});
		}
		return collection;
	}

	@Override
	public String getName() {
		return userInfo.getName();
	}

	public String getUsername() {
		return userInfo.getUsername();
	}
}

