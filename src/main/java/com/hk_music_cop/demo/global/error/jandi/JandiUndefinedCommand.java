package com.hk_music_cop.demo.global.error.jandi;

public class JandiUndefinedCommand extends JandiException {
	private static final String DEFAULT_MESSAGE = "적절하지 않은 명령어입니다.";

	public JandiUndefinedCommand(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public JandiUndefinedCommand() {
		super(DEFAULT_MESSAGE);
	}
}
