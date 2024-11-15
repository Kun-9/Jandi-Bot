package com.hk_music_cop.demo.lottery.dto.request;



public record LotteryRequest(Long memberId, String lotteryName, String position) {

	// 로터리 추가를 위한 함수
	public static LotteryRequest of(Long memberId, LotteryCreateRequest lotteryCreateRequest) {
		return new LotteryRequest(memberId, lotteryCreateRequest.lotteryName(), lotteryCreateRequest.position());
	}
}
