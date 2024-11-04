package com.hk_music_cop.demo.global.error.jandi;

public class JandiDuplicatedNameException extends JandiException {

	private static final String DEFAULT_MESSAGE = "이미 등록된 이름입니다.";

	public JandiDuplicatedNameException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public JandiDuplicatedNameException() {
		super(DEFAULT_MESSAGE);
	}
}
