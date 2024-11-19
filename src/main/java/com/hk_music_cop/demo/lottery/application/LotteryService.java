package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotterySimple;

import java.util.List;


public interface LotteryService {
	LotteryResponse chooseLotteryWinner();

	void registerLottery(LotteryRequest lotteryRequest);

	void deleteLottery(Long memberId, String lotteryName);

	void deleteLotteryByManager(String lotteryName);

	void updateLottery(Long memberId, LotteryUpdateRequest lottery);

	List<LotteryResponse> getAllLottery();

	void validateExistByName(String name);

	void validateExistById(Long lotteryId);

	// 해당 로터리 생성자인지 확인
	void validateCreator(Long memberId, Long lotteryId);

	void validateDuplicateName(String name);

	LotteryResponse findByName(String name);
}
