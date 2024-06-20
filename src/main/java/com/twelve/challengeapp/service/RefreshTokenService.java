package com.twelve.challengeapp.service;

import com.twelve.challengeapp.entity.RefreshToken;
import com.twelve.challengeapp.entity.User;

public interface RefreshTokenService {
	RefreshToken findByToken(String token);

	RefreshToken updateRefreshToken(User user);

	void deleteRefreshToken(String username);
}
