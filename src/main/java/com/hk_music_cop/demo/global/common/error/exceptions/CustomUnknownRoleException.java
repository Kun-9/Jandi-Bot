package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ResponseCode;

public class CustomUnknownRoleException extends CustomException {
	public CustomUnknownRoleException() {
		super(ResponseCode.UNKNOWN_ROLE);
	}

	public CustomUnknownRoleException(String detail) {
		super(ResponseCode.UNKNOWN_ROLE, detail);
	}
}