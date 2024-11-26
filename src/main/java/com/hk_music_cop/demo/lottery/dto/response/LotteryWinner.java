package com.hk_music_cop.demo.lottery.dto.response;

public record LotteryWinner(String lotteryName, String position) {
	public static LotteryWinner from(LotteryDetailResponse lotteryDetailResponse) {
		return new LotteryWinner(lotteryDetailResponse.getLotteryName(), lotteryDetailResponse.getPosition());
	}
}
