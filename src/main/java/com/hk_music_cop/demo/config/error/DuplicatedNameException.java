package com.hk_music_cop.demo.config.error;

public class DuplicatedNameException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "이미 등록된 이름입니다.";

	public DuplicatedNameException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public DuplicatedNameException() {
		super(DEFAULT_MESSAGE);
	}
}
