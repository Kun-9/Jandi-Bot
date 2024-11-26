package com.hk_music_cop.demo.global.common.error.exceptions;

import com.hk_music_cop.demo.global.common.response.ErrorCode;

public class CustomLotteryNotFoundException extends CustomException {
	public CustomLotteryNotFoundException() {
		super(ErrorCode.LOTTERY_NOT_FOUND);
	}

	public CustomLotteryNotFoundException(String detail) {
		super(ErrorCode.LOTTERY_NOT_FOUND, detail);
	}
}