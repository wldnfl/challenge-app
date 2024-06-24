package com.twelve.challengeapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.exception.AlreadyAdminException;
import com.twelve.challengeapp.exception.UserNotFoundException;
import com.twelve.challengeapp.repository.CommentRepository;
import com.twelve.challengeapp.repository.PostRepository;
import com.twelve.challengeapp.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public AdminServiceImpl(UserRepository userRepository, PostRepository postRepository,
		CommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		return userRepository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UserResponseDto updateUserRole(Long userId, UserRequestDto.Role role) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

		user.updateRole(UserRole.from(role.getRole()));

		return new UserResponseDto(user);
	}

	@Override
	@Transactional
	public UserResponseDto promoteToAdmin(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

		if (user.getRole().equals(UserRole.ADMIN)) {
			throw new AlreadyAdminException("User is already an admin");
		}

		user.updateRole(UserRole.ADMIN);

		return new UserResponseDto(user);
	}

	@Override
	@Transactional
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

		// post, comment 상태는 유지
		user.updateRole(UserRole.DELETED);
	}
}
