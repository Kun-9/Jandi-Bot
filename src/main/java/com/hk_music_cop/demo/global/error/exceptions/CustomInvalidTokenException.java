package com.hk_music_cop.demo.global.error.exceptions;

public class CustomInvalidTokenException extends CustomException {
	private static final String DEFAULT_MESSAGE = "유효하지 않은 토큰 입니다.";

	public CustomInvalidTokenException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomInvalidTokenException() {
		super(DEFAULT_MESSAGE);
	}
}
