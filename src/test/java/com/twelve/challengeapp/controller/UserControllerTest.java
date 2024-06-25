package com.twelve.challengeapp.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twelve.challengeapp.config.TestConfig;
import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;
import com.twelve.challengeapp.entity.User;
import com.twelve.challengeapp.entity.UserRole;
import com.twelve.challengeapp.jwt.UserDetailsImpl;
import com.twelve.challengeapp.service.UserPasswordService;
import com.twelve.challengeapp.service.UserService;

@WebMvcTest(
	controllers = {UserController.class, UserPasswordController.class})
@Import(TestConfig.class)
class UserControllerTest {

	private static final String BASE_URL = "/api/users";
	private static final String TEST_USERNAME = "testuser";
	private static final String TEST_PASSWORD = "TestPass123!";
	private static final String TEST_NICKNAME = "TestNick";
	private static final String TEST_INTRO = "Hello, I'm a test user";
	private static final String TEST_EMAIL = "test@example.com";
	private static final String NEW_NICKNAME = "NewNick";
	private static final String NEW_INTRO = "Updated introduction";
	private static final String NEW_PASSWORD = "NewPass123!";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	@MockBean
	private UserPasswordService userPasswordService;
	@Autowired
	private ObjectMapper objectMapper;

	private UserRequestDto.Register registerDto;
	private UserRequestDto.EditInfo editDto;
	private UserRequestDto.Withdrawal withdrawalDto;
	private UserRequestDto.ChangePassword changePasswordDto;
	private UserResponseDto userResponseDto;
	private User user;
	private UserDetailsImpl userDetails;

	@BeforeEach
	void setUp() {
		user = User.builder()
			.id(1L)
			.username(TEST_USERNAME)
			.password(TEST_PASSWORD)
			.nickname(TEST_NICKNAME)
			.introduce(TEST_INTRO)
			.email(TEST_EMAIL)
			.role(UserRole.USER)
			.build();

		userDetails = new UserDetailsImpl(user);

		registerDto = UserRequestDto.Register.builder()
			.username(TEST_USERNAME)
			.password(TEST_PASSWORD)
			.nickname(TEST_NICKNAME)
			.introduce(TEST_INTRO)
			.email(TEST_EMAIL)
			.build();

		editDto = UserRequestDto.EditInfo.builder()
			.password(NEW_PASSWORD)
			.nickname(NEW_NICKNAME)
			.introduce(NEW_INTRO)
			.build();

		withdrawalDto = UserRequestDto.Withdrawal.builder().username(TEST_USERNAME).password(TEST_PASSWORD).build();

		changePasswordDto = UserRequestDto.ChangePassword.builder()
			.username(TEST_USERNAME)
			.password(TEST_PASSWORD)
			.changePassword(NEW_PASSWORD)
			.build();

		userResponseDto = new UserResponseDto(TEST_USERNAME, TEST_NICKNAME, TEST_INTRO, TEST_EMAIL);
	}

	@Test
	void registerUser_Success() throws Exception {
		// Given
		doNothing().when(userService).registerUser(any(UserRequestDto.Register.class));

		// When
		ResultActions resultActions = mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(registerDto)));

		// Then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("The request has been successfully processed."))
			.andExpect(jsonPath("$.status").value(200));

		verify(userService).registerUser(any(UserRequestDto.Register.class));
	}

	@Test
	void getUser_Success() throws Exception {
		// Given
		when(userService.getUser(any(UserDetailsImpl.class))).thenReturn(userResponseDto);

		// When
		ResultActions resultActions = mockMvc.perform(
			get(BASE_URL).with(user(userDetails)).contentType(MediaType.APPLICATION_JSON));

		// Then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.username").value(TEST_USERNAME))
			.andExpect(jsonPath("$.data.nickname").value(TEST_NICKNAME))
			.andExpect(jsonPath("$.data.introduce").value(TEST_INTRO))
			.andExpect(jsonPath("$.data.email").value(TEST_EMAIL));

		verify(userService).getUser(any(UserDetailsImpl.class));
	}

	@Test
	void editUser_Success() throws Exception {
		// Given
		UserResponseDto updatedUserResponseDto = new UserResponseDto(TEST_USERNAME, NEW_NICKNAME, NEW_INTRO,
			TEST_EMAIL);
		when(userService.editUser(any(UserRequestDto.EditInfo.class), any(UserDetailsImpl.class))).thenReturn(
			updatedUserResponseDto);

		// When
		ResultActions resultActions = mockMvc.perform(put(BASE_URL).with(user(userDetails))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(editDto)));

		// Then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.nickname").value(NEW_NICKNAME))
			.andExpect(jsonPath("$.data.introduce").value(NEW_INTRO));

		verify(userService).editUser(any(UserRequestDto.EditInfo.class), any(UserDetailsImpl.class));
	}

	@Test
	void withdraw_Success() throws Exception {
		// Given
		doNothing().when(userService).withdraw(any(UserRequestDto.Withdrawal.class), any(UserDetailsImpl.class));

		// When
		ResultActions resultActions = mockMvc.perform(delete(BASE_URL).with(user(userDetails))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(withdrawalDto)));

		// Then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("The request has been successfully processed."))
			.andExpect(jsonPath("$.status").value(200));

		verify(userService).withdraw(any(UserRequestDto.Withdrawal.class), any(UserDetailsImpl.class));
	}

	@Test
	@DisplayName("비밀번호 수정")
	void Change_Password_Success() throws Exception {
		// Given
		UserResponseDto userResponseDto = new UserResponseDto(TEST_USERNAME, TEST_NICKNAME, TEST_INTRO,
			TEST_EMAIL);
		when(userPasswordService.userPasswordChange(any(UserRequestDto.ChangePassword.class),
			any(UserDetailsImpl.class))).thenReturn(
			userResponseDto);

		// When
		ResultActions resultActions = mockMvc.perform(put("/api/users/password").with(user(userDetails))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(changePasswordDto)));

		// Then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.username").value(TEST_USERNAME))
			.andExpect(jsonPath("$.data.nickname").value(TEST_NICKNAME))
			.andExpect(jsonPath("$.data.introduce").value(TEST_INTRO))
			.andExpect(jsonPath("$.data.email").value(TEST_EMAIL));

		verify(userPasswordService).userPasswordChange(any(UserRequestDto.ChangePassword.class),
			any(UserDetailsImpl.class));
	}
}
