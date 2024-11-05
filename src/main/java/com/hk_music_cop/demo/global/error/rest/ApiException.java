package com.hk_music_cop.demo.global.error.rest;

public class ApiException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "API 호출 오류입니다.";

	public ApiException(String message ) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public ApiException() {
		super(DEFAULT_MESSAGE);
	}

}
