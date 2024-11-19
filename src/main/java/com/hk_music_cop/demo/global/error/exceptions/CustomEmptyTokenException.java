package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomEmptyTokenException extends CustomException {
	public CustomEmptyTokenException() {
		super(ResponseCode.EMPTY_TOKEN);
	}

	public CustomEmptyTokenException(String detail) {
		super(ResponseCode.EMPTY_TOKEN, detail);
	}
}