package com.hk_music_cop.demo.lottery.dto.response;

public record LotteryWinner(String lotteryName, String position) {
	public static LotteryWinner from(LotteryResponse lotteryResponse) {
		return new LotteryWinner(lotteryResponse.getLotteryName(), lotteryResponse.getPosition());
	}
}
