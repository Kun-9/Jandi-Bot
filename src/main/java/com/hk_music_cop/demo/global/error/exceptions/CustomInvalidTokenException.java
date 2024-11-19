package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomInvalidTokenException extends CustomException {
	public CustomInvalidTokenException() {
		super(ResponseCode.INVALID_TOKEN);
	}

	public CustomInvalidTokenException(String detail) {
		super(ResponseCode.INVALID_TOKEN, detail);
	}
}