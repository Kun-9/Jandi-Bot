package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomUsernameNotFoundException extends CustomException {
	public CustomUsernameNotFoundException() {
		super(ErrorCode.USERNAME_NOT_FOUND);
	}

	public CustomUsernameNotFoundException(String detail) {
		super(ErrorCode.USERNAME_NOT_FOUND, detail);
	}
}