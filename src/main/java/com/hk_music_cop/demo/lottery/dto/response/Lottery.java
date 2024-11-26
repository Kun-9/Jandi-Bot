package com.hk_music_cop.demo.lottery.dto.response;

import com.hk_music_cop.demo.lottery.common.enums.Position;

public record Lottery(Long lotteryId, String lotteryName, String position) {

	public static Lottery of(Long lotteryId, String lotteryName, String position) {
		return new Lottery(lotteryId, lotteryName, position);
	}
}
