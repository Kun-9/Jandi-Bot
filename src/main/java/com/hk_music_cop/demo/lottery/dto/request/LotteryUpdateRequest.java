package com.hk_music_cop.demo.lottery.dto.request;

public record LotteryUpdateRequest(String targetName, String name, String position) {
	public static LotteryUpdateRequest of(String targetName, String name, String position) {
		return new LotteryUpdateRequest(targetName, name, position);
	}
	public static LotteryUpdateRequest of(String targetName, LotteryRequest lotteryRequest) {
		return new LotteryUpdateRequest(targetName, lotteryRequest.lotteryName(), lotteryRequest.position());
	}
}
