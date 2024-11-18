package com.hk_music_cop.demo.global.error.exceptions;

public class CustomTokenExpiredException extends CustomException {
	private static final String DEFAULT_MESSAGE = "토큰이 만료되었습니다.";

	public CustomTokenExpiredException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomTokenExpiredException() {
		super(DEFAULT_MESSAGE);
	}
}
