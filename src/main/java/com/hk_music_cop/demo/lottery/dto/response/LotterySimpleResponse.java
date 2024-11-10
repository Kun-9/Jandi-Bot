package com.hk_music_cop.demo.lottery.dto.response;

import lombok.Value;

@Value
public class LotterySimpleResponse {
	String name;
	String position;

	public static LotterySimpleResponse from(LotteryResponse lotteryResponse) {

		return new LotterySimpleResponse(lotteryResponse.getName(), lotteryResponse.getPosition());
	}

}
