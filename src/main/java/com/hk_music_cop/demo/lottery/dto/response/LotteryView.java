package com.hk_music_cop.demo.lottery.dto.response;

public record LotteryView(String lotteryName, String position) {
	public static LotteryView from(LotteryResponse lotteryResponse) {
		return new LotteryView(lotteryResponse.getLotteryName(), lotteryResponse.getPosition());
	}
}
