package com.hk_music_cop.demo.global.error.common;

public class CustomDuplicatedUserIdException extends CustomException {
	private static final String DEFAULT_MESSAGE = "이미 존재하는 회원 아이디입니다.";

	public CustomDuplicatedUserIdException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomDuplicatedUserIdException() {
		super(DEFAULT_MESSAGE);
	}
}
