package com.twelve.challengeapp.config.dto;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.twelve.challengeapp.dto.UserRequestDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

public class UserRequestDtoTest {

	private static Validator validator;

	@BeforeAll
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testValidRegisterDto() {
		UserRequestDto.Register dto = UserRequestDto.Register
			.builder()
			.username("validuser")
			.password("ValidPass12!")
			.nickname("Valid Nickname")
			.introduce("Valid introduction")
			.email("valid@email.com")
			.build();

		Set<ConstraintViolation<UserRequestDto.Register>> violations = validator.validate(dto);
		assertTrue(violations.isEmpty());
	}

	@Test
	void testInvalidUsername() {
		UserRequestDto.Register dto = UserRequestDto.Register
			.builder()
			.username("Invalid User")
			.password("ValidPass12!")
			.nickname("Valid Nickname")
			.introduce("Valid introduction")
			.email("valid@email.com")
			.build();

		Set<ConstraintViolation<UserRequestDto.Register>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
	}

	@Test
	void testInvalidPassword() {
		UserRequestDto.Register dto = UserRequestDto.Register
			.builder()
			.username("Invalid User")
			.password("weekpassword")
			.nickname("Valid Nickname")
			.introduce("Valid introduction")
			.email("valid@email.com")
			.build();

		Set<ConstraintViolation<UserRequestDto.Register>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
	}

}
