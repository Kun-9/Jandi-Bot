package com.hk_music_cop.demo.global.error.exceptions;

public class CustomExpiredRefreshTokenException extends CustomException {

	private static final String DEFAULT_MESSAGE = "만료된 Token 입니다.";

	public CustomExpiredRefreshTokenException(String message ) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomExpiredRefreshTokenException() {
		super(DEFAULT_MESSAGE);
	}

}
