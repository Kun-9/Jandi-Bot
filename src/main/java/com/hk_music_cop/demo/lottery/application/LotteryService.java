package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotteryUpdateLog;
import com.hk_music_cop.demo.lottery.dto.response.LotteryWinner;

import java.util.List;


public interface LotteryService {
	LotteryWinner drawLotteryWinner();

	Long registerLottery(LotteryRequest lotteryRequest);

	void deleteLottery(Long memberId, String lotteryName);

	void deleteLotteryByManager(String lotteryName);

	LotteryUpdateLog updateLottery(Long memberId, LotteryUpdateRequest lottery);

	List<LotteryResponse> getAllLottery();

	void validateExistByName(String name);

	void validateExistById(Long lotteryId);

	// 해당 로터리 생성자인지 확인
	void validateCreator(Long memberId, Long lotteryId);

	void validateDuplicateName(String name);

	LotteryResponse findByName(String name);
}
