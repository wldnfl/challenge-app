package com.twelve.challengeapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

public class UserRequestDto {

	private static final String USERNAME_MSG = "Username must contain only lowercase letters and numbers";
	private static final String PASSWORD_MSG = "EditInfo must contain at least one uppercase letter, one lowercase letter, one number, and one special character";
	private static final String USERNAME_REGEXP = "^[a-z0-9]*$";
	private static final String PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$";

	private static final int USERNAME_MIN = 4;
	private static final int USERNAME_MAX = 10;

	private static final int PASSWORD_MIN = 8;
	private static final int PASSWORD_MAX = 15;

	@Getter
	private static class UserInfo {
		@NotBlank
		@Size(min = USERNAME_MIN, max = USERNAME_MAX)
		@Pattern(regexp = USERNAME_REGEXP, message = USERNAME_MSG)
		private final String username;

		@NotBlank
		@Size(min = PASSWORD_MIN, max = PASSWORD_MAX)
		@Pattern(regexp = PASSWORD_REGEXP, message = PASSWORD_MSG)
		private final String password;

		public UserInfo(String username, String password) {
			this.username = username;
			this.password = password;
		}
	}

	@Getter
	public static class Register extends UserInfo {

		@NotBlank
		private final String nickname;
		@NotBlank
		private final String introduce;
		@Email
		private final String email;

		@Builder
		public Register(String username, String password, String nickname, String introduce, String email) {
			super(username, password);
			this.nickname = nickname;
			this.introduce = introduce;
			this.email = email;
		}
	}

	@Getter
	public static class Login extends UserInfo {
		@Builder
		public Login(String username, String password) {
			super(username, password);
		}
	}

	//회원 정보 수정
	@Getter
	@Builder
	public static class EditInfo {

		@NotBlank
		@Size(min = PASSWORD_MIN, max = PASSWORD_MAX)
		@Pattern(regexp = PASSWORD_REGEXP, message = PASSWORD_MSG)
		private String password;
		@NotBlank
		private String nickname;
		@NotBlank
		private String introduce;
	}

	//비밀 번호 수정
	@Getter
	public static class ChangePassword extends UserInfo {
		@NotBlank
		@Size(min = PASSWORD_MIN, max = PASSWORD_MAX)
		@Pattern(regexp = PASSWORD_REGEXP, message = PASSWORD_MSG)
		private final String changePassword;

		@Builder
		public ChangePassword(String username, String password, String changePassword) {
			super(username, password);
			this.changePassword = changePassword;
		}
	}

	//회원 탈퇴
	@Getter
	public static class Withdrawal extends UserInfo {
		@Builder
		public Withdrawal(String username, String password) {
			super(username, password);
		}
	}

	@Getter
	public static class Role {

		private String role;
	}
}
