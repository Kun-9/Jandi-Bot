package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;


public class CustomDuplicatedNameException extends CustomException {
	public CustomDuplicatedNameException() {
		super(ErrorCode.DUPLICATE_NAME);
	}

	public CustomDuplicatedNameException(String detail) {
		super(ErrorCode.DUPLICATE_NAME, detail);
	}
}