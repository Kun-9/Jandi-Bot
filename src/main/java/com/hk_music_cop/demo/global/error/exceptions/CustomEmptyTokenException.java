package com.hk_music_cop.demo.global.error.exceptions;

public class CustomEmptyTokenException extends CustomException {
	private static final String DEFAULT_MESSAGE = "토큰이 제공되지 않았습니다.";

	public CustomEmptyTokenException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomEmptyTokenException() {
		super(DEFAULT_MESSAGE);
	}
}
