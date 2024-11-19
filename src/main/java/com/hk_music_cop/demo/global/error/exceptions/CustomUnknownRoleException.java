package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomUnknownRoleException extends CustomException {
	public CustomUnknownRoleException() {
		super(ResponseCode.UNKNOWN_ROLE);
	}

	public CustomUnknownRoleException(String detail) {
		super(ResponseCode.UNKNOWN_ROLE, detail);
	}
}