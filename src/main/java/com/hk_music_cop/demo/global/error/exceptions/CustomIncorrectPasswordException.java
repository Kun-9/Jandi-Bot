package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomIncorrectPasswordException extends CustomException {
	public CustomIncorrectPasswordException() {
		super(ResponseCode.INCORRECT_PASSWORD);
	}

	public CustomIncorrectPasswordException(String detail) {
		super(ResponseCode.INCORRECT_PASSWORD, detail);
	}
}