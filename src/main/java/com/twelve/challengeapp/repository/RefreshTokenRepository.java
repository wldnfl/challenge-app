package com.twelve.challengeapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twelve.challengeapp.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	Boolean existsByUsername(String username);

	RefreshToken findByUsername(String username);

	void deleteByUsername(String username);
}
