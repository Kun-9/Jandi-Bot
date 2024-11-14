package com.hk_music_cop.demo.lottery.dto.response;

import lombok.Value;

@Value
public class LotterySimpleResponse {
	String lotteryName;
	String position;

	public static LotterySimpleResponse from(LotteryResponse lotteryResponse) {

		return new LotterySimpleResponse(lotteryResponse.getLotteryName(), lotteryResponse.getPosition());
	}

}
