package com.twelve.challengeapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class UserRequestDto {

	@Getter
	private static class UserInfo {
		@NotBlank
		@Size(min = 4, max = 10)
		@Pattern(regexp = "^[a-z0-9]*$", message = "Username must contain only lowercase letters and numbers")
		private String username;

		@NotBlank
		@Size(min = 8, max = 15)
		@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$", message = "EditInfo must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
		private String password;
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
	@Getter
	public static class EditInfo extends Register{
	}
	@Getter
	public static class Withdrawal extends UserInfo {
	}


}
