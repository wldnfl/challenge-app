package com.twelve.challengeapp.service;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserPasswordRecord;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.exception.DuplicateUsernameException;
import com.twelve.challengeapp.exception.PasswordMismatchException;
import com.twelve.challengeapp.exception.UsernameMismatchException;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.repository.UserPasswordRepository;
import com.twelve.challengeapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserPasswordRepository userPasswordRepository;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserPasswordRepository userPasswordRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userPasswordRepository = userPasswordRepository;
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

		UserPasswordRecord userPasswordRecord = new UserPasswordRecord(user, passwordEncoder.encode(requestDto.getPassword()));
		userRepository.save(user);
		//비밀번호 저장
		userPasswordRepository.save(userPasswordRecord);
	}
	//회원 정보
	@Override
	public UserResponseDto getUser(UserDetailsImpl userDetails) {

		return new UserResponseDto(
				userDetails.getUsername(),
				userDetails.getNickname(),
				userDetails.getIntroduce(),
				userDetails.getEmail());
	}
	//회원 정보 수정
	@Override
	public UserResponseDto editUser(UserRequestDto.EditInfo requestDto, UserDetailsImpl userDetails) {
		// 비밀번호 확인
		if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
			throw new PasswordMismatchException("Passwords do not match");
		}

		User user = userDetails.getUser();
		user.editInfo(requestDto.getNickname(),requestDto.getIntroduce());

		userRepository.save(user);

		return new UserResponseDto(
				userDetails.getUsername(),
				userDetails.getNickname(),
				userDetails.getIntroduce(),
				userDetails.getEmail());
	}
	//회원 탈퇴
	@Override
	public void withdraw(UserRequestDto.Withdrawal requestDto, UserDetailsImpl userDetails) {

		// 요청된 사용자 이름과 현재 로그인한 사용자가 일치하는지 확인
		if (!requestDto.getUsername().equals(userDetails.getUsername())) {
			throw new UsernameMismatchException("Login ID does not match");
		}

		// 비밀번호 확인
		if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
			throw new PasswordMismatchException("Passwords do not match");
		}

		User user = userDetails.getUser();
		user.updateRole(UserRole.WITHDRAWAL);

		userRepository.save(user);
	}


}
