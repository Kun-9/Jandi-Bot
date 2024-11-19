package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomNotFoundException extends CustomException {
	public CustomNotFoundException() {
		super(ResponseCode.NOT_FOUND);
	}

	public CustomNotFoundException(String detail) {
		super(ResponseCode.NOT_FOUND, detail);
	}
}