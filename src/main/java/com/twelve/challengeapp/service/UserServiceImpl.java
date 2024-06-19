package com.twelve.challengeapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.exception.DuplicateUsernameException;
import com.twelve.challengeapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void registerUser(UserRequestDto.Register requestDto) {

		if (userRepository.existsByUsername(requestDto.getUsername())) {
			throw new DuplicateUsernameException("Duplicate username.");
		}

		User user = User.builder()
			.username(requestDto.getUsername())
			.password(passwordEncoder.encode(requestDto.getPassword()))
			.nickname(requestDto.getNickname())
			.introduce(requestDto.getIntroduce())
			.email(requestDto.getEmail())
			.role(UserRole.USER)
			.build();

		userRepository.save(user);
	}

}
