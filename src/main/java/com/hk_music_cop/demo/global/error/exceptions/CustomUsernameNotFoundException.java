package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomUsernameNotFoundException extends CustomException {
	public CustomUsernameNotFoundException() {
		super(ResponseCode.USERNAME_NOT_FOUND);
	}

	public CustomUsernameNotFoundException(String detail) {
		super(ResponseCode.USERNAME_NOT_FOUND, detail);
	}
}