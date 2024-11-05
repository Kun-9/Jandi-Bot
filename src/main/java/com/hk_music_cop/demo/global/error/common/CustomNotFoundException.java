package com.hk_music_cop.demo.global.error.common;

public class CustomNotFoundException extends CustomException {

	private static final String DEFAULT_MESSAGE = "해당 값이나 리소스를 찾지 못했습니다.";

	public CustomNotFoundException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomNotFoundException() {
		super(DEFAULT_MESSAGE);
	}
}
