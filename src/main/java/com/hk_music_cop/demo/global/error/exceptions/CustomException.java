package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ResponseCode responseCode;

	public CustomException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.responseCode = responseCode;
	}

	public CustomException(ResponseCode responseCode, String detail) {
		super(responseCode.getMessage() + " : " + detail);
		this.responseCode = responseCode;
	}
}