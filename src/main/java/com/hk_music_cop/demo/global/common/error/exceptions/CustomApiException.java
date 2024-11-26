package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomApiException extends CustomException {
	public CustomApiException() {
		super(ErrorCode.API_ERROR);
	}

	public CustomApiException(String detail) {
		super(ErrorCode.API_ERROR, detail);
	}
}
