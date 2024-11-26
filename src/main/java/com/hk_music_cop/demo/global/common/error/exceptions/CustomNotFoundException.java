package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomNotFoundException extends CustomException {
	public CustomNotFoundException() {
		super(ErrorCode.NOT_FOUND);
	}

	public CustomNotFoundException(String detail) {
		super(ErrorCode.NOT_FOUND, detail);
	}
}