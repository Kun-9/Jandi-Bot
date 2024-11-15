package com.hk_music_cop.demo.global.error.exceptions;

public class CustomUnauthorizedException extends CustomException {

	private static final String DEFAULT_MESSAGE = "권한이 없습니다.";

	public CustomUnauthorizedException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomUnauthorizedException() {
		super(DEFAULT_MESSAGE);
	}
}
