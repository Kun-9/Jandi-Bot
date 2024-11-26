package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomUnknownMemberException extends CustomException {
	public CustomUnknownMemberException() {
		super(ErrorCode.UNKNOWN_MEMBER);
	}

	public CustomUnknownMemberException(String detail) {
		super(ErrorCode.UNKNOWN_MEMBER, detail);
	}
}