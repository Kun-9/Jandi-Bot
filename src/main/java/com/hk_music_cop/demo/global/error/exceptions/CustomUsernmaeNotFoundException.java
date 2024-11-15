package com.hk_music_cop.demo.global.error.exceptions;

public class CustomUsernmaeNotFoundException extends CustomException {
	private static final String DEFAULT_MESSAGE = "유저 이름을 찾을 수 없습니다.";

	public CustomUsernmaeNotFoundException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomUsernmaeNotFoundException() {
		super(DEFAULT_MESSAGE);
	}
}
