package com.hk_music_cop.demo.global.error.exceptions;

public class CustomUndefinedCommand extends CustomException {
	private static final String DEFAULT_MESSAGE = "적절하지 않은 명령어입니다.";

	public CustomUndefinedCommand(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomUndefinedCommand() {
		super(DEFAULT_MESSAGE);
	}
}
