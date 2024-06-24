package com.twelve.challengeapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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

	private UserRequestDto.ChangePassword changePasswordDto;
	private User user;
	private UserDetailsImpl userDetails;

	@BeforeEach
	void setUp() {
		changePasswordDto = UserRequestDto.ChangePassword.builder()
			.changePassword("newPassword1234!")
			.username("testuser")
			.password("Password1!")
			.build();

		user = User.builder()
			.id(1L)
			.username("testuser")
			.password("encodedPassword")
			.nickname("Lee")
			.introduce("Hi!")
			.email("test@example.com")
			.role(UserRole.USER)
			.build();

		userDetails = new UserDetailsImpl(user);
	}

	@Test
	@DisplayName("비밀번호 변경 성공")
	void testUserPasswordChange_Success() {
		// Given
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(userPasswordRepository.findTop3ByUserIdOrderByCreatedAtDesc(anyLong())).thenReturn(
			Collections.emptyList());

		// When
		UserResponseDto responseDto = userPasswordService.userPasswordChange(changePasswordDto, userDetails);

		// Then
		assertNotNull(responseDto);
		assertEquals("testuser", responseDto.getUsername());
		verify(userRepository, times(1)).save(any(User.class));
		verify(userPasswordRepository, never()).findByUserIdAndCreatedAt(anyLong(), any());
	}

	@Test
	@DisplayName("사용자가 일치하지 않는 경우")
	void testChangePasswordWithNonExistingUser() {
		// Given
		given(userRepository.findByUsername(anyString())).willReturn(Optional.empty());

		// When & Then
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			userPasswordService.userPasswordChange(changePasswordDto, userDetails);
		});

		String expectedMessage = "The user name does not exist";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	@DisplayName("현재 비밀번호가 일치하지 않는 경우")
	void testChangePassword_PasswordNotMatch() {
		// Given
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

		// When & Then
		PasswordMismatchException exception = assertThrows(PasswordMismatchException.class, () -> {
			userPasswordService.userPasswordChange(changePasswordDto, userDetails);
		});

		assertEquals("Passwords do not match", exception.getMessage());
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	@DisplayName("최근 3개의 비밀번호와 일치한 경우")
	void testChangePasswordWithRecentlyUsedPassword() {
		// Given
		UserPasswordRecord record1 = new UserPasswordRecord("encodedPassword1");
		UserPasswordRecord record2 = new UserPasswordRecord("encodedPassword2");
		UserPasswordRecord record3 = new UserPasswordRecord("encodedPassword3");

		given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));
		given(passwordEncoder.matches(anyString(), anyString())).willReturn(true); // 현재 비밀번호 일치
		given(userPasswordRepository.findTop3ByUserIdOrderByCreatedAtDesc(anyLong()))
			.willReturn(Arrays.asList(record1, record2, record3));
		given(passwordEncoder.encode(anyString())).willReturn("encodedNewPassword");
		given(passwordEncoder.matches(changePasswordDto.getChangePassword(), record1.getUserPassword()))
			.willReturn(false);
		given(passwordEncoder.matches(changePasswordDto.getChangePassword(), record2.getUserPassword()))
			.willReturn(false);
		given(passwordEncoder.matches(changePasswordDto.getChangePassword(), record3.getUserPassword()))
			.willReturn(true); // 최근 비밀번호와 일치

		// When & Then
		PasswordMismatchException exception = assertThrows(PasswordMismatchException.class, () -> {
			userPasswordService.userPasswordChange(changePasswordDto, userDetails);
		});

		assertEquals("Your new password is the same as a recently used password. Please choose a different password.",
			exception.getMessage());

		verify(userRepository, times(1)).findByUsername(anyString());
		verify(userPasswordRepository, times(1)).findTop3ByUserIdOrderByCreatedAtDesc(anyLong());
		verify(passwordEncoder, times(1)).matches(changePasswordDto.getPassword(), userDetails.getPassword());
		verify(passwordEncoder, times(1)).matches(changePasswordDto.getChangePassword(), record1.getUserPassword());
		verify(passwordEncoder, times(1)).matches(changePasswordDto.getChangePassword(), record2.getUserPassword());
		verify(passwordEncoder, times(1)).matches(changePasswordDto.getChangePassword(), record3.getUserPassword());
	}
}
