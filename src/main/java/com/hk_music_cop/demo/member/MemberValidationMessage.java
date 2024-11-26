package com.hk_music_cop.demo.member;

public class MemberValidationMessage {
	public static final class Login {




	}

	public static final class SignUp {
		public static final String USER_ID_BLANK = "{validation.auth.signUp.request.userId.notBlank}";
		public static final String USER_ID_FORMAT = "{validation.auth.signUp.request.userId.format}";
		public static final String USER_ID_SIZE = "{validation.auth.signUp.request.userId.size}";
		public static final String PASSWORD_BLANK = "{validation.auth.signUp.request.password.notBlank}";
		public static final String PASSWORD_FORMAT = "{validation.auth.signUp.request.password.format}";
		public static final String PASSWORD_SIZE = "{validation.auth.signUp.request.password.size}";
		public static final String NAME_BLANK = "{validation.auth.signUp.request.name.blank}";
		public static final String NAME_SIZE = "{validation.auth.signUp.request.name.size}";
		public static final String NAME_FORMAT = "{validation.auth.signUp.request.name.format}";
	}
}
