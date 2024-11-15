package com.hk_music_cop.demo.global.error.exceptions;

public class CustomUnkownRoleException extends CustomApiException {
	private static final String DEFAULT_MESSAGE = "없는 역할입니다. 관리자에게 문의해주세요.";

	public CustomUnkownRoleException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomUnkownRoleException() {
		super(DEFAULT_MESSAGE);
	}
}
