package com.hk_music_cop.demo.global.error.jandi;

public class JandiUnauthorizedException extends JandiException {

	private static final String DEFAULT_MESSAGE = "권한이 없습니다.";

	public JandiUnauthorizedException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public JandiUnauthorizedException() {
		super(DEFAULT_MESSAGE);
	}
}
