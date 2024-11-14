package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;

import java.util.List;


public interface LotteryService {
	LotteryResponse chooseLotteryWinner();

	boolean registerLottery(LotteryRequest lotteryRequest);

	boolean deleteLottery(Long memberId, String lotteryName);

	boolean updateLottery(Long memberId, Long lotteryId, LotteryRequest lotteryRequest);

	List<LotteryResponse> getAllLottery();

	LotteryResponse validateExistByName(String name);

	void validateExistById(Long lotteryId);

	// 해당 로터리 생성자인지 확인
	void validCreator(Long memberId, Long lotteryId);

	void validateDuplicateName(String name);

	void validateNotExist(String name);

}
