package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomExpiredRefreshTokenException extends CustomException {
	public CustomExpiredRefreshTokenException() {
		super(ErrorCode.EXPIRED_REFRESH_TOKEN);
	}

	public CustomExpiredRefreshTokenException(String detail) {
		super(ErrorCode.EXPIRED_REFRESH_TOKEN, detail);
	}
}