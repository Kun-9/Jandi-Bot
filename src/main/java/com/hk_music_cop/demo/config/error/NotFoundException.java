package com.hk_music_cop.demo.config.error;

public class NotFoundException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "해당 값이나 리소스를 찾지 못했습니다.";

	public NotFoundException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public NotFoundException() {
		super(DEFAULT_MESSAGE);
	}
}
