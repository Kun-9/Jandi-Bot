package com.hk_music_cop.demo.global.error.jandi;

public class LotteryNotFoundException extends RuntimeException {
	private static final String DEFAULT_MESSAGE = "해당 lottery를 찾을 수 없습니다.";

	public LotteryNotFoundException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public LotteryNotFoundException() {
		super(DEFAULT_MESSAGE);
	}
}
