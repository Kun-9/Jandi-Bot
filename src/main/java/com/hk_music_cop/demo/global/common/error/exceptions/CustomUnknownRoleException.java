package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomUnknownRoleException extends CustomException {
	public CustomUnknownRoleException() {
		super(ErrorCode.UNKNOWN_ROLE);
	}

	public CustomUnknownRoleException(String detail) {
		super(ErrorCode.UNKNOWN_ROLE, detail);
	}
}