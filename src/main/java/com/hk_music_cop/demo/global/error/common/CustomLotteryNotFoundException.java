package com.hk_music_cop.demo.global.error.common;

public class CustomLotteryNotFoundException extends CustomException {
	private static final String DEFAULT_MESSAGE = "해당 lottery를 찾을 수 없습니다.";

	public CustomLotteryNotFoundException(String message) {
		super(DEFAULT_MESSAGE + " : " + message);
	}

	public CustomLotteryNotFoundException() {
		super(DEFAULT_MESSAGE);
	}
}
