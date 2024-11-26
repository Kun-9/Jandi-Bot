package com.hk_music_cop.demo.member.message;

public class MemberValidationMessage {
	public static final class Login {


	}

	public static final class SignUp {
		public static final String USER_ID_BLANK = "{validation.auth.userId.notBlank}";
		public static final String USER_ID_FORMAT = "{validation.auth.signUp.userId.format}";
		public static final String USER_ID_SIZE = "{validation.auth.signUp.userId.size}";
		public static final String PASSWORD_BLANK = "{validation.auth.password.notBlank}";
		public static final String PASSWORD_FORMAT = "{validation.auth.signUp.password.format}";
		public static final String PASSWORD_SIZE = "{validation.auth.signUp.password.size}";
		public static final String NAME_BLANK = "{validation.auth.signUp.name.blank}";
		public static final String NAME_SIZE = "{validation.auth.signUp.name.size}";
		public static final String NAME_FORMAT = "{validation.auth.signUp.name.format}";
	}
}
