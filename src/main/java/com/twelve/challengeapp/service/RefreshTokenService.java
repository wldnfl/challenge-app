package com.twelve.challengeapp.service;

import com.twelve.challengeapp.entity.RefreshToken;

public interface RefreshTokenService {
	RefreshToken findByToken(String token);
}
