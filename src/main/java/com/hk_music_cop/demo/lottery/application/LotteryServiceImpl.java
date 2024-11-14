package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.global.error.common.CustomDuplicatedNameException;
import com.hk_music_cop.demo.global.error.common.CustomNotFoundException;
import com.hk_music_cop.demo.global.error.common.CustomUnauthorizedException;
import com.hk_music_cop.demo.global.error.common.CustomLotteryNotFoundException;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
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
	public boolean registerLottery(LotteryRequest lotteryRequest) {

		validateDuplicateName(lotteryRequest.lotteryName());

		return lotteryRepository.createLottery(lotteryRequest) > 0;
	}


	@Override
	public boolean deleteLottery(Long memberId, String lotteryName) {
		LotteryResponse targetLottery = validateExistByName(lotteryName);
		validCreator(memberId, targetLottery.getLotteryId());

		return lotteryRepository.deleteLottery(targetLottery.getLotteryId()) == 1;
	}

	@Override
	public boolean updateLottery(Long memberId, Long lotteryId, LotteryRequest lotteryRequest) {

		// 타겟 lottery 존재 확인
		validateExistById(lotteryId);

		// 권한 확인 (요청자가 생성자인지)
		validCreator(memberId, lotteryId);

		LotteryResponse targetLottery = lotteryRepository.findByLotteryId(lotteryId).get();

		// 바꿀 이름 존재 여부 확인 :: 동시성 고려?
		validateNotExist(targetLottery.getLotteryName());

		return lotteryRepository.editLottery(lotteryId, lotteryRequest) == 1;
	}

	@Override
	public LotteryResponse validateExistByName(String name) {
		return lotteryRepository.findByName(name)
				.orElseThrow(() -> new CustomNotFoundException(name));
	}

	@Override
	public void validateExistById(Long lotteryId) {
		lotteryRepository.findByLotteryId(lotteryId)
				.orElseThrow(CustomNotFoundException::new);
	}

	@Override
	public void validCreator(Long memberId, Long lotteryId) {
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
	public void validateNotExist(String name) {
		if (!lotteryRepository.existsByName(name)) {
			throw new CustomLotteryNotFoundException(name);
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
