package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomExpiredRefreshTokenException extends CustomException {
	public CustomExpiredRefreshTokenException() {
		super(ResponseCode.EXPIRED_REFRESH_TOKEN);
	}

	public CustomExpiredRefreshTokenException(String detail) {
		super(ResponseCode.EXPIRED_REFRESH_TOKEN, detail);
	}
}