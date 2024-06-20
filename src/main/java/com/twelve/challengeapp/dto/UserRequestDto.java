package com.twelve.challengeapp.dto;

import com.twelve.challengeapp.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

	@Getter
	private static class UserInfo {
		@NotBlank
		@Size(min = 4, max = 10)
		@Pattern(regexp = "^[a-z0-9]*$", message = "Username must contain only lowercase letters and numbers")
		private String username;

		@NotBlank
		@Size(min = 8, max = 15)
		@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
		private String password;

		//유저 권한 및 탈퇴 유저 확인
		private final UserRole role = UserRole.USER;
	}

	@Getter
	public static class Register extends UserInfo {

		@NotBlank
		private String nickname;
		@NotBlank
		private String introduce;
		@Email
		private String email;
	}

	@Getter
	public static class Login extends UserInfo {
	}

}
