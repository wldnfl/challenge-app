package com.twelve.challengeapp.service;

import org.springframework.stereotype.Service;

import com.twelve.challengeapp.entity.RefreshToken;
import com.twelve.challengeapp.exception.TokenNotFoundException;
import com.twelve.challengeapp.repository.RefreshTokenRepository;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public RefreshToken findByToken(String token) {
		return refreshTokenRepository.findByToken(token)
			.orElseThrow(() -> new TokenNotFoundException("Not found Refresh Token."));
	}
}
