package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.ex.ResponseCode;
import com.hk_music_cop.demo.global.error.exceptions.*;
import com.hk_music_cop.demo.lottery.dto.request.Lottery;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.response.LotterySimple;
import com.hk_music_cop.demo.lottery.repository.LotteryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class LotteryServiceImpl implements LotteryService {

	private final Random rand = new Random();
	private final LotteryRepository lotteryRepository;

	@Override
	public LotteryResponse findByName(String name) {
		return lotteryRepository.findByName(name)
				.orElseThrow(CustomNotFoundException::new);
	}

	public LotteryResponse chooseLotteryWinner() {
		List<LotteryResponse> personList = lotteryRepository.findAll();

		if (personList.isEmpty()) {
			throw new CustomLotteryNotFoundException("추첨리스트가 존재하지 않습니다.");
		}

		return getRandom(personList);
	}

	private LotteryResponse getRandom(List<LotteryResponse> personList) {
		return personList.get(rand.nextInt(personList.size()));
	}

	@Override
	public void registerLottery(LotteryRequest lotteryRequest) {

		validateDuplicateName(lotteryRequest.lotteryName());

		if (lotteryRepository.createLottery(lotteryRequest) != 1)
			throw new CustomException(ResponseCode.DATABASE_CREATE_ERROR);
	}

	@Override
	public void deleteLottery(Long memberId, String lotteryName) {
		LotteryResponse targetLottery = findByName(lotteryName);

		validateCreator(memberId, targetLottery.getLotteryId());

		boolean result = lotteryRepository.deleteLottery(targetLottery.getLotteryId()) == 1;
		if (!result) throw new CustomException(ResponseCode.DATABASE_DELETE_ERROR);

	}

	@Override
	public void deleteLotteryByManager(String lotteryName) {
		LotteryResponse targetLottery = findByName(lotteryName);

		boolean result = lotteryRepository.deleteLottery(targetLottery.getLotteryId()) == 1;
		if (!result) throw new CustomException(ResponseCode.DATABASE_DELETE_ERROR);
	}

	@Override
	public void updateLottery(Long memberId, LotteryUpdateRequest lottery) {

		LotteryResponse targetLottery = findByName(lottery.targetName());

		// 권한 확인 (요청자가 생성자인지)
		validateCreator(memberId, targetLottery.getLotteryId());

		if (lotteryRepository.editLottery(targetLottery.getLotteryId(), LotterySimple.from(lottery)) != 1)
			throw new CustomException(ResponseCode.DATABASE_UPDATE_ERROR);
	}

	@Override
	public void validateExistByName(String name) {
		if (lotteryRepository.existsByName(name)) {
			throw new CustomNotFoundException(name);
		}
	}

	@Override
	public void validateExistById(Long lotteryId) {
		lotteryRepository.findByLotteryId(lotteryId)
				.orElseThrow(CustomNotFoundException::new);
	}

	@Override
	public void validateCreator(Long memberId, Long lotteryId) {
		boolean isCreator = lotteryRepository.isCreatedBy(memberId, lotteryId);
		if (!isCreator) throw new CustomUnauthorizedException();
	}

	@Override
	public void validateDuplicateName(String name) {
		if (lotteryRepository.existsByName(name)) {
			throw new CustomDuplicatedNameException(name);
		}
	}

	@Override
	public List<LotteryResponse> getAllLottery() {
		List<LotteryResponse> list = lotteryRepository.findAll();
		if (list.isEmpty()) {
			throw new CustomNotFoundException("추첨 리스트가 존재하지 않습니다.");
		}
		return list;
	}
}
