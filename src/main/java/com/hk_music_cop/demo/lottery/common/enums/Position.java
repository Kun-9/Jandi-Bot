package com.hk_music_cop.demo.lottery.common.enums;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ErrorCode;

public enum Position {
	주임, 대리, 과장, 차장, 부장, 이사, 상무, 전무;

	public static void validate(String name) {
		try {
			Position position = valueOf(name);

		} catch (IllegalArgumentException e) {
			throw new CustomException(ErrorCode.LOTTERY_POSITION_FORMAT);
		}
	}
}
