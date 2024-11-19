package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ResponseCode;

public class CustomUnauthorizedException extends CustomException {
	public CustomUnauthorizedException() {
		super(ResponseCode.UNAUTHORIZED);
	}

	public CustomUnauthorizedException(String detail) {
		super(ResponseCode.UNAUTHORIZED, detail);
	}
}