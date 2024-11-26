package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomUndefinedCommand extends CustomException {
	public CustomUndefinedCommand() {
		super(ErrorCode.UNDEFINED_COMMAND);
	}

	public CustomUndefinedCommand(String detail) {
		super(ErrorCode.UNDEFINED_COMMAND, detail);
	}
}