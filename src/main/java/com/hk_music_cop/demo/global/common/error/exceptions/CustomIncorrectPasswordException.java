package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomIncorrectPasswordException extends CustomException {
	public CustomIncorrectPasswordException() {
		super(ErrorCode.INCORRECT_PASSWORD);
	}

	public CustomIncorrectPasswordException(String detail) {
		super(ErrorCode.INCORRECT_PASSWORD, detail);
	}
}