package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomInvalidTokenException extends CustomException {
	public CustomInvalidTokenException() {
		super(ErrorCode.INVALID_TOKEN);
	}

	public CustomInvalidTokenException(String detail) {
		super(ErrorCode.INVALID_TOKEN, detail);
	}
}