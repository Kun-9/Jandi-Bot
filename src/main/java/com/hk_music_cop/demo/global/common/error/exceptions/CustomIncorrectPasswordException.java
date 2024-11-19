package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ResponseCode;

public class CustomIncorrectPasswordException extends CustomException {
	public CustomIncorrectPasswordException() {
		super(ResponseCode.INCORRECT_PASSWORD);
	}

	public CustomIncorrectPasswordException(String detail) {
		super(ResponseCode.INCORRECT_PASSWORD, detail);
	}
}