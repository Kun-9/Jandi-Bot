package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomTokenExpiredException extends CustomException {
	public CustomTokenExpiredException() {
		super(ErrorCode.TOKEN_EXPIRED);
	}

	public CustomTokenExpiredException(String detail) {
		super(ErrorCode.TOKEN_EXPIRED, detail);
	}
}