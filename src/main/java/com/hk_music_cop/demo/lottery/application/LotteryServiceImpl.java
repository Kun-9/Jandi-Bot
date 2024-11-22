package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.global.common.error.exceptions.*;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.request.LotteryUpdateRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.LotterySimple;
import com.hk_music_cop.demo.lottery.dto.response.LotteryUpdateLog;
import com.hk_music_cop.demo.lottery.dto.response.LotteryWinner;
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
				.orElseThrow(CustomLotteryNotFoundException::new);
	}

	public LotteryWinner drawLotteryWinner() {
		List<LotteryResponse> personList = lotteryRepository.findAll();

		if (personList.isEmpty()) {
			throw new CustomLotteryNotFoundException("추첨리스트가 존재하지 않습니다.");
		}

		return LotteryWinner.from(getRandom(personList));
	}

	private LotteryResponse getRandom(List<LotteryResponse> personList) {
		return personList.get(rand.nextInt(personList.size()));
	}

	@Override
	public Long registerLottery(LotteryRequest lotteryRequest) {

		validateDuplicateName(lotteryRequest.lotteryName());

		Long lotteryId = lotteryRepository.createLottery(lotteryRequest);
		if (lotteryId == 0)
			throw new CustomException(ResponseCode.DATABASE_CREATE_ERROR);

		return lotteryId;
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

		log.info("[관리자 권한으로 삭제] : {}", lotteryName);
	}

	@Override
	public LotteryUpdateLog updateLottery(Long memberId, LotteryUpdateRequest lottery) {

		LotteryResponse targetLottery = findByName(lottery.targetName());

		// 변경가능한 값인지 확인
//		validatePossibleUpdate(LotterySimple.from(lottery), LotterySimple.from(targetLottery));

		// 권한 확인 (요청자가 생성자인지)
		validateCreator(memberId, targetLottery.getLotteryId());

		if (lotteryRepository.editLottery(targetLottery.getLotteryId(), LotterySimple.from(lottery)) != 1)
			throw new CustomException(ResponseCode.DATABASE_UPDATE_ERROR);

		LotteryUpdateLog lotteryUpdateLog = LotteryUpdateLog.of(LotterySimple.from(targetLottery), LotterySimple.from(lottery));

		log.info("[Lottery 변경] memberId : {}, log : {}" , memberId, lotteryUpdateLog);

		return lotteryUpdateLog;
	}

	private void validatePossibleUpdate(LotterySimple lotteryTarget, LotterySimple lotteryToUpdate) {
		// 변경하고자 하는 로터리와, 업데이트할 로터리의 이름이 같은지 확인
		if (lotteryToUpdate.lotteryName().equals(lotteryTarget.lotteryName())) {
			// 포지션도 동일하다면, 동일한 로터리 오류 발생
			if (lotteryToUpdate.position().equals(lotteryTarget.position()))
				throw new CustomException(ResponseCode.LOTTERY_EQUALS);
		} else {
			// 이름이 같지 않은데, 존재하는 이름이라면 오류 발생
			if (validationExistByName(lotteryTarget.lotteryName()))
				throw new CustomException(ResponseCode.LOTTERY_DUPLICATE_NAME);
		}
	}

	private boolean validationExistByName(String lotteryName) {
		return lotteryRepository.existsByName(lotteryName);
	}

	private void validateEquals(LotterySimple lottery, LotterySimple lotteryTarget) {
		if (lotteryTarget.equals(lottery)) throw new CustomException(ResponseCode.LOTTERY_EQUALS);
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
