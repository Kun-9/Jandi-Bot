package com.hk_music_cop.demo.lottery.dto.request;

public record Lottery(Long lotteryId, String lotteryName, String position) {

	public static Lottery of(Long lotteryId, String lotteryName, String position) {
		return new Lottery(lotteryId, lotteryName, position);
	}
}
