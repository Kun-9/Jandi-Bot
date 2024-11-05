package com.hk_music_cop.demo.global.error.jandi;

public class DuplicatedUserIdException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "이미 존재하는 회원 아이디입니다.";

	public DuplicatedUserIdException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public DuplicatedUserIdException() {
		super(DEFAULT_MESSAGE);
	}
}
