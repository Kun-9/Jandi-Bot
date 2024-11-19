package com.hk_music_cop.demo.global.error.exceptions;

import com.hk_music_cop.demo.ex.ResponseCode;

public class CustomLotteryNotFoundException extends CustomException {
	public CustomLotteryNotFoundException() {
		super(ResponseCode.LOTTERY_NOT_FOUND);
	}

	public CustomLotteryNotFoundException(String detail) {
		super(ResponseCode.LOTTERY_NOT_FOUND, detail);
	}
}