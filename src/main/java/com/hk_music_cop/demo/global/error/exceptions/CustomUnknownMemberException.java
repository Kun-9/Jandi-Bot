package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomUnknownMemberException extends CustomException {
	public CustomUnknownMemberException() {
		super(ResponseCode.UNKNOWN_MEMBER);
	}

	public CustomUnknownMemberException(String detail) {
		super(ResponseCode.UNKNOWN_MEMBER, detail);
	}
}