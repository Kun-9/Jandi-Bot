package com.hk_music_cop.demo.lottery.dto.request;

import lombok.*;

@Getter @ToString
public class LotteryRequest {
	private Long memberId;
	private final String lotteryName;
	private final String position;

	public LotteryRequest(String lotteryName, String position) {
		this.lotteryName = lotteryName;
		this.position = position;
	}

	public LotteryRequest(Long memberId, String lotteryName, String position) {
		this.memberId = memberId;
		this.lotteryName = lotteryName;
		this.position = position;
	}

	public LotteryRequest withMemberId(Long memberId) {
		return new LotteryRequest(memberId, this.lotteryName, this.position);
	}
}
