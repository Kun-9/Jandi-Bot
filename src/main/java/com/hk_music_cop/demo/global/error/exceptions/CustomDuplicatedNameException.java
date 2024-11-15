package com.hk_music_cop.demo.global.error.exceptions;

public class CustomDuplicatedNameException extends CustomException {

	private static final String DEFAULT_MESSAGE = "이미 등록된 이름입니다.";

	public CustomDuplicatedNameException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomDuplicatedNameException() {
		super(DEFAULT_MESSAGE);
	}
}
