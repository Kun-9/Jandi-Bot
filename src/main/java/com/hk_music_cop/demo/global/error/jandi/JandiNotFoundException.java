package com.hk_music_cop.demo.global.error.jandi;

public class JandiNotFoundException extends JandiException {

	private static final String DEFAULT_MESSAGE = "해당 값이나 리소스를 찾지 못했습니다.";

	public JandiNotFoundException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public JandiNotFoundException() {
		super(DEFAULT_MESSAGE);
	}
}
