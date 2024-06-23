package com.twelve.challengeapp.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
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
import com.twelve.challengeapp.service.AuthService;

@WebMvcTest(AuthController.class)
@Import(TestConfig.class)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@Autowired
	private ObjectMapper objectMapper;

	private UserRequestDto.Login loginDto;

	private static final String TEST_USERNAME = "test1234";
	private static final String TEST_PASSWORD = "Test1234!@";
	private static final String TEST_TOKEN = "test-token";
	private static final String LOGIN_URL = "/api/auth/login";
	private static final String LOGOUT_URL = "/api/auth/logout";
	private static final String AUTH_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@BeforeEach
	void setUp() {
		loginDto = UserRequestDto.Login.builder().username(TEST_USERNAME).password(TEST_PASSWORD).build();
	}

	@Test
	void login_Success() throws Exception {
		// Given
		when(authService.login(any(UserRequestDto.Login.class))).thenReturn(TEST_TOKEN);

		// When
		ResultActions resultActions = mockMvc.perform(
			post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDto)));

		// Then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(TEST_TOKEN))
			.andExpect(jsonPath("$.message").value("The request has been successfully processed."))
			.andExpect(jsonPath("$.status").value(200));

		verify(authService).login(
			argThat(dto -> dto.getUsername().equals(TEST_USERNAME) && dto.getPassword().equals(TEST_PASSWORD)));
	}

	@Test
	void logout_Success() throws Exception {
		// Given
		String authHeader = BEARER_PREFIX + TEST_TOKEN;

		// When
		ResultActions resultActions = mockMvc.perform(delete(LOGOUT_URL).header(AUTH_HEADER, authHeader));

		// Then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("The request has been successfully processed."))
			.andExpect(jsonPath("$.status").value(200));

		verify(authService).logout(authHeader);
	}
}
