package com.hk_music_cop.demo.global.error.exceptions;

public class CustomApiException extends CustomException {

	private static final String DEFAULT_MESSAGE = "API 호출 오류입니다.";

	public CustomApiException(String message ) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomApiException() {
		super(DEFAULT_MESSAGE);
	}

}
