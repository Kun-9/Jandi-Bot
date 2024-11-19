package com.hk_music_cop.demo.lottery.dto.response;

public record LotteryUpdateLog(LotterySimple before, LotterySimple after) {
	public static LotteryUpdateLog of(LotterySimple before, LotterySimple after) {
		return new LotteryUpdateLog(before, after);
	}
}
