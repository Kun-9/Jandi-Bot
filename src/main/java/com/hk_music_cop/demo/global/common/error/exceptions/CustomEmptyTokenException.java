package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomEmptyTokenException extends CustomException {
	public CustomEmptyTokenException() {
		super(ErrorCode.EMPTY_TOKEN);
	}

	public CustomEmptyTokenException(String detail) {
		super(ErrorCode.EMPTY_TOKEN, detail);
	}
}