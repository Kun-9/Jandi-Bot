package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ResponseCode;

public class CustomUsernameNotFoundException extends CustomException {
	public CustomUsernameNotFoundException() {
		super(ResponseCode.USERNAME_NOT_FOUND);
	}

	public CustomUsernameNotFoundException(String detail) {
		super(ResponseCode.USERNAME_NOT_FOUND, detail);
	}
}