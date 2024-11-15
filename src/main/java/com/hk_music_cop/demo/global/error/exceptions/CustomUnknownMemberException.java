package com.hk_music_cop.demo.global.error.exceptions;

public class CustomUnknownMemberException extends CustomException {
	private static final String DEFAULT_MESSAGE = "등록되지 않은 회원입니다.";

	public CustomUnknownMemberException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomUnknownMemberException() {
		super(DEFAULT_MESSAGE);
	}
}
