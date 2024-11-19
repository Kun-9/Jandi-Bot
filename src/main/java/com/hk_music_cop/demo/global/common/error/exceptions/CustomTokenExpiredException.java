package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ResponseCode;

public class CustomTokenExpiredException extends CustomException {
	public CustomTokenExpiredException() {
		super(ResponseCode.TOKEN_EXPIRED);
	}

	public CustomTokenExpiredException(String detail) {
		super(ResponseCode.TOKEN_EXPIRED, detail);
	}
}