package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomDuplicatedUserIdException extends CustomException {
	public CustomDuplicatedUserIdException() {
		super(ErrorCode.DUPLICATE_USER_ID);
	}

	public CustomDuplicatedUserIdException(String detail) {
		super(ErrorCode.DUPLICATE_USER_ID, detail);
	}
}