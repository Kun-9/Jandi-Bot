package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;


public class CustomDuplicatedNameException extends CustomException {
	public CustomDuplicatedNameException() {
		super(ResponseCode.DUPLICATE_NAME);
	}

	public CustomDuplicatedNameException(String detail) {
		super(ResponseCode.DUPLICATE_NAME, detail);
	}
}