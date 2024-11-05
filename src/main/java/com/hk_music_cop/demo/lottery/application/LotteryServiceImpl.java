package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.global.error.jandi.JandiDuplicatedNameException;
import com.hk_music_cop.demo.global.error.jandi.JandiNotFoundException;
import com.hk_music_cop.demo.global.error.jandi.JandiUnauthorizedException;
import com.hk_music_cop.demo.global.error.jandi.LotteryNotFoundException;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.repository.LotteryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class LotteryServiceImpl implements LotteryService {


	private final Random rand = new Random();
	private final LotteryRepository lotteryRepository;

	public LotteryResponse chooseLotteryWinner(String title, String color, String imgURL) {
		List<LotteryResponse> personList = lotteryRepository.findAll();

		return getRandom(personList);
	}

	private LotteryResponse getRandom(List<LotteryResponse> personList) {
		return personList.get(rand.nextInt(personList.size()));
	}

	@Override
	public boolean registerLottery(LotteryRequest lotteryRequest) {

		validateDuplicateName(lotteryRequest.getName());

		return lotteryRepository.createLottery(lotteryRequest) > 0;
	}


	@Override
	public boolean deleteLottery(Long memberId, String name) {
		LotteryResponse targetLottery = validateExistByName(name);
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
		validateNotExist(targetLottery.getName());

		return lotteryRepository.editLottery(lotteryId, lotteryRequest) == 1;
	}

	@Override
	public LotteryResponse validateExistByName(String name) {
		return lotteryRepository.findByName(name)
				.orElseThrow(() -> new JandiNotFoundException(name));
	}

	@Override
	public void validateExistById(Long lotteryId) {
		lotteryRepository.findByLotteryId(lotteryId)
				.orElseThrow(JandiNotFoundException::new);
	}

	@Override
	public void validCreator(Long memberId, Long lotteryId) {
		boolean isCreator = lotteryRepository.isCreatedBy(memberId, lotteryId);
		if (!isCreator) throw new JandiUnauthorizedException();
	}

	@Override
	public void validateDuplicateName(String name) {
		if (lotteryRepository.existsByName(name)) {
			throw new JandiDuplicatedNameException(name);
		}
	}

	@Override
	public void validateNotExist(String name) {
		if (!lotteryRepository.existsByName(name)) {
			throw new LotteryNotFoundException(name);
		}
	}
}
