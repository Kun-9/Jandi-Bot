package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;

import java.util.List;


public interface LotteryService {
	LotteryResponse chooseLotteryWinner(String title, String color, String imgURL);

	boolean registerLottery(LotteryRequest lotteryRequest);

	boolean deleteLottery(Long memberId, String name);

	boolean updateLottery(Long memberId, Long lotteryId, LotteryRequest lotteryRequest);

	List<LotteryResponse> getAllLottery();

	LotteryResponse validateExistByName(String name);

	void validateExistById(Long lotteryId);

	void validCreator(Long memberId, Long lotteryId);

	void validateDuplicateName(String name);

	void validateNotExist(String name);

}
