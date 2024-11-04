package com.hk_music_cop.demo.global.error.rest;

public class ApiException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "이미 등록된 이름입니다.";

	public ApiException(String message ) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public ApiException() {
		super(DEFAULT_MESSAGE);
	}

}
