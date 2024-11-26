package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomUnauthorizedException extends CustomException {
	public CustomUnauthorizedException() {
		super(ErrorCode.UNAUTHORIZED);
	}

	public CustomUnauthorizedException(String detail) {
		super(ErrorCode.UNAUTHORIZED, detail);
	}
}