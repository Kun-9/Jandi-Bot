package com.hk_music_cop.demo.lottery.dto.response;

public record LotteryUpdateLog(LotteryResponse before, LotteryResponse after) {
	public static LotteryUpdateLog of(LotteryResponse before, LotteryResponse after) {
		return new LotteryUpdateLog(before, after);
	}
}
