package com.hk_music_cop.demo.lottery.dto.response;

import java.util.List;

public record LotteryViewList(List<LotteryView> lotteryViews) {
	public static LotteryViewList from(List<LotteryView> lotteryViewList) {
		return new LotteryViewList(lotteryViewList);
	}
}
