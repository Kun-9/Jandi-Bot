package com.hk_music_cop.demo.lottery.dto.response;

import com.hk_music_cop.demo.lottery.common.enums.Position;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;

public record LotteryResponse(String lotteryName, String position) {
	public static LotteryResponse from(LotteryDetailResponse lotteryDetailResponse) {
		return new LotteryResponse(lotteryDetailResponse.getLotteryName(), lotteryDetailResponse.getPosition());
	}

	public static LotteryResponse from(LotteryResponse lotteryResponse) {
		return new LotteryResponse(lotteryResponse.lotteryName(), lotteryResponse.position());
	}

	public static LotteryResponse from(LotteryUpdateRequest lottery) {
		return new LotteryResponse(lottery.name(), lottery.position());
	}

	public static LotteryResponse of(String lotteryName, String position) {
		return new LotteryResponse(lotteryName, position);
	}
}
