package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ResponseCode;

public class CustomDuplicatedUserIdException extends CustomException {
	public CustomDuplicatedUserIdException() {
		super(ResponseCode.DUPLICATE_USER_ID);
	}

	public CustomDuplicatedUserIdException(String detail) {
		super(ResponseCode.DUPLICATE_USER_ID, detail);
	}
}