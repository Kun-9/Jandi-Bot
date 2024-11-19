package com.hk_music_cop.demo.lottery.dto;

import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import lombok.Value;

@Value
public record LotterySimple(String lotteryName, String position) {
	public static LotterySimple from(LotteryResponse lotteryResponse) {
		return new LotterySimple(lotteryResponse.getLotteryName(), lotteryResponse.getPosition());
	}

	public static LotterySimple from(LotteryUpdateRequest lottery) {
		return new LotterySimple(lottery.name(), lottery.position());
	}

	public static LotterySimple of(String lotteryName, String position) {
		return new LotterySimple(lotteryName, position);
	}
}
