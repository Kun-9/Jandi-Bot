package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomDuplicatedUserIdException extends CustomException {
	public CustomDuplicatedUserIdException() {
		super(ResponseCode.DUPLICATE_USER_ID);
	}

	public CustomDuplicatedUserIdException(String detail) {
		super(ResponseCode.DUPLICATE_USER_ID, detail);
	}
}