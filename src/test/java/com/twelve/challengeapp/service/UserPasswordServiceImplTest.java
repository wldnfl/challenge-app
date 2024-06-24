package com.twelve.challengeapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.anyLong;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserPasswordRecord;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.exception.PasswordMismatchException;
import com.twelve.challengeapp.exception.UserNotFoundException;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.repository.UserPasswordRepository;
import com.twelve.challengeapp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserPasswordServiceImplTest {

	@Mock
	private UserPasswordRepository userPasswordRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserPasswordServiceImpl userPasswordService;

	private UserRequestDto.Register registerDto;
	private UserRequestDto.ChangePassword changePasswordDto;
	private User user;
	private UserDetailsImpl userDetails;
	private UserPasswordRecord userPasswordRecord;
	private static final String TEST_USERNAME = "testuser";
	private static final String TEST_PASSWORD = "Password1!";
	private static final String TEST_NICKNAME = "Lee";
	private static final String TEST_INTRO = "Hi!";
	private static final String TEST_EMAIL = "test@example.com";
	private static final String ENCODED_PASSWORD = "encodedPassword";
	private static final String NEW_PASSWORD = "newPassword1234!";

	@BeforeEach
	void setUp() {

		registerDto = UserRequestDto.Register.builder()
			.username(TEST_USERNAME)
			.password(TEST_PASSWORD)
			.nickname(TEST_NICKNAME)
			.introduce(TEST_INTRO)
			.email(TEST_EMAIL)
			.build();

		changePasswordDto = UserRequestDto.ChangePassword.builder()
			.changePassword(NEW_PASSWORD)
			.username(TEST_USERNAME)
			.password(TEST_PASSWORD)
			.build();

		user = User.builder()
			.id(1L)
			.username(TEST_USERNAME)
			.password(ENCODED_PASSWORD)
			.nickname(TEST_NICKNAME)
			.introduce(TEST_INTRO)
			.email(TEST_EMAIL)
			.role(UserRole.USER)
			.build();

		userDetails = new UserDetailsImpl(user);
	}

	@Test
	@DisplayName("비밀번호 변경 성공")
	void testUserPasswordChange_Success() {
		//when
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
		when(userPasswordRepository.findTop3ByUserIdOrderByCreatedAtDesc(anyLong())).thenReturn(
			Collections.emptyList());

		UserResponseDto responseDto = userPasswordService.userPasswordChange(changePasswordDto, userDetails);

		// then
		assertNotNull(responseDto);
		assertEquals(TEST_USERNAME, responseDto.getUsername());
		verify(userRepository, times(1)).save(any(User.class));
		verify(userPasswordRepository, never()).findByUserIdAndCreatedAt(anyLong(), any());
	}

	@Test
	@DisplayName("해당 유저가 일치하지 않는 경우")
	void registerUser_DuplicateUsername() {
		// Given
		when(userRepository.existsByUsername(any())).thenReturn(true);

		// When & Then
		assertThrows(UserNotFoundException.class,
			() -> userPasswordService.userPasswordChange(changePasswordDto, userDetails));

		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	@DisplayName("현재 비밀번호가 일치하지 않는 경우")
	void registerUser_PasswordNotMatch() {
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

		// Act & Assert
		PasswordMismatchException exception = assertThrows(PasswordMismatchException.class, () -> {
			userPasswordService.userPasswordChange(changePasswordDto, userDetails);
		});

		assertEquals("Passwords do not match", exception.getMessage());
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	@DisplayName("최근 3개의 비밀번호와 일치한 경우")
	public void testChangePasswordWithRecentlyUsedPassword() {
		// 사용자와 비밀번호 레코드 설정
		UserPasswordRecord record1 = new UserPasswordRecord("encodedPassword1");
		UserPasswordRecord record2 = new UserPasswordRecord("encodedPassword2");
		UserPasswordRecord record3 = new UserPasswordRecord("encodedPassword3");

		given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));
		given(passwordEncoder.matches(anyString(), anyString())).willReturn(true); // 현재 비밀번호 일치
		given(userPasswordRepository.findTop3ByUserIdOrderByCreatedAtDesc(any(Long.class)))
			.willReturn(Arrays.asList(record1, record2, record3));
		given(passwordEncoder.encode(anyString())).willReturn("encodedNewPassword");
		given(passwordEncoder.matches(changePasswordDto.getChangePassword(), record1.getUserPassword()))
			.willReturn(false);
		given(passwordEncoder.matches(changePasswordDto.getChangePassword(), record2.getUserPassword()))
			.willReturn(false);
		given(passwordEncoder.matches(changePasswordDto.getChangePassword(), record3.getUserPassword()))
			.willReturn(true); // 최근 비밀번호와 일치

		// 테스트 실행 및 검증
		PasswordMismatchException exception = Assertions.assertThrows(PasswordMismatchException.class, () -> {
			userPasswordService.userPasswordChange(changePasswordDto, userDetails);
		});

		Assertions.assertEquals(
			"Your new password is the same as a recently used password. Please choose a different password.",
			exception.getMessage());

		verify(userRepository, times(1)).findByUsername(anyString());
		verify(userPasswordRepository, times(1)).findTop3ByUserIdOrderByCreatedAtDesc(any(Long.class));
		verify(passwordEncoder, times(1)).matches(changePasswordDto.getPassword(), userDetails.getPassword());
		verify(passwordEncoder, times(1)).matches(changePasswordDto.getChangePassword(), record1.getUserPassword());
		verify(passwordEncoder, times(1)).matches(changePasswordDto.getChangePassword(), record2.getUserPassword());
		verify(passwordEncoder, times(1)).matches(changePasswordDto.getChangePassword(), record3.getUserPassword());
	}
}
