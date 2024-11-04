package com.hk_music_cop.demo.global.error.rest;

public class DuplicatedNameException extends RestException {

	private static final String DEFAULT_MESSAGE = "이미 등록된 이름입니다.";

	public DuplicatedNameException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public DuplicatedNameException() {
		super(DEFAULT_MESSAGE);
	}
}
