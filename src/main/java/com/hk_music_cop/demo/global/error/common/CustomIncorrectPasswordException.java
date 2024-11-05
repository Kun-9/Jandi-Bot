package com.hk_music_cop.demo.global.error.common;

public class CustomIncorrectPasswordException extends CustomException {
	private static final String DEFAULT_MESSAGE = "비밀번호가 일치하지 않습니다.";

	public CustomIncorrectPasswordException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomIncorrectPasswordException() {
		super(DEFAULT_MESSAGE);
	}
}
