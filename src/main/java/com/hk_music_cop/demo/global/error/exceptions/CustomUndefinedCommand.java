package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomUndefinedCommand extends CustomException {
	public CustomUndefinedCommand() {
		super(ResponseCode.UNDEFINED_COMMAND);
	}

	public CustomUndefinedCommand(String detail) {
		super(ResponseCode.UNDEFINED_COMMAND, detail);
	}
}