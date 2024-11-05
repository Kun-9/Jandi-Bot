package com.hk_music_cop.demo.global.error.jandi;

public class UnkownMemberException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "등록되지 않은 회원입니다.";

	public UnkownMemberException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public UnkownMemberException() {
		super(DEFAULT_MESSAGE);
	}
}
