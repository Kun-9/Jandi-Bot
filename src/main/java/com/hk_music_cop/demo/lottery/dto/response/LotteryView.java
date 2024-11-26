package com.hk_music_cop.demo.lottery.dto.response;

import com.hk_music_cop.demo.lottery.common.enums.Position;

public record LotteryView(String lotteryName, String position) {
	public static LotteryView from(LotteryDetailResponse lotteryDetailResponse) {
		return new LotteryView(lotteryDetailResponse.getLotteryName(), lotteryDetailResponse.getPosition());
	}
}
