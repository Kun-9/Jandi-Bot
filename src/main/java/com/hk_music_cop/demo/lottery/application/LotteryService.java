package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;


public interface LotteryService {
	LotteryResponse chooseLotteryWinner(String title, String color, String imgURL);

	Long registPerson(LotteryRequest request);

	boolean deletePerson(Long memberId, String name);

	boolean updatePerson(Long memberId, Long lotteryId, LotteryRequest lotteryRequest);
}
