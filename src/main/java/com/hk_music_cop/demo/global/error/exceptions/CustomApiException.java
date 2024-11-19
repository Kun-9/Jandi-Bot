package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomApiException extends CustomException {
	public CustomApiException() {
		super(ResponseCode.API_ERROR);
	}

	public CustomApiException(String detail) {
		super(ResponseCode.API_ERROR, detail);
	}
}
