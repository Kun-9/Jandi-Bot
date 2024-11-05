package com.hk_music_cop.demo.global.error.jandi;

public class IncorrectPasswordException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "비밀번호가 일치하지 않습니다.";

	public IncorrectPasswordException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public IncorrectPasswordException() {
		super(DEFAULT_MESSAGE);
	}
}
