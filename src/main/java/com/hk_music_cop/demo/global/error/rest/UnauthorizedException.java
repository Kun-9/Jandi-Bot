package com.hk_music_cop.demo.global.error.rest;

public class UnauthorizedException extends RestException {

	private static final String DEFAULT_MESSAGE = "권한이 없습니다.";

	public UnauthorizedException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public UnauthorizedException() {
		super(DEFAULT_MESSAGE);
	}
}
