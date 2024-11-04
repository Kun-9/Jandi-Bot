package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.global.error.jandi.JandiDuplicatedNameException;
import com.hk_music_cop.demo.global.error.jandi.JandiNotFoundException;
import com.hk_music_cop.demo.global.error.jandi.JandiUnauthorizedException;
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
	@Transactional
	public Long registPerson(LotteryRequest lotteryRequest) {

		validateDuplicateName(lotteryRequest.getName());

		return lotteryRepository.createLottery(lotteryRequest);
	}


	@Override
	public boolean deletePerson(Long memberId, String name) {
		LotteryResponse targetLottery = validateExistByName(name);
		validCreator(memberId, targetLottery.getLotteryId());

		return lotteryRepository.deleteLottery(targetLottery.getLotteryId()) > 0;
	}

	@Override
	public boolean updatePerson(Long memberId, Long lotteryId, LotteryRequest lotteryRequest) {

		// 타겟 lottery 존재 확인
		validateExistById(lotteryId);

		// 권한 확인 (요청자가 생성자인지)
		validCreator(memberId, lotteryId);

		// 바꿀 이름 존재 여부 확인 :: 동시성 고려?
		validateDuplicateName(lotteryRequest.getName());

		return lotteryRepository.editLottery(lotteryId, lotteryRequest) > 0;
	}

	private LotteryResponse validateExistByName(String name) {
		return lotteryRepository.findByName(name)
				.orElseThrow(() -> new JandiNotFoundException(name));
	}

	private void validateExistById(Long lotteryId) {
		lotteryRepository.findByLotteryId(lotteryId)
				.orElseThrow(JandiNotFoundException::new);
	}

	private void validCreator(Long memberId, Long lotteryId) {
		boolean isCreator = lotteryRepository.isCreatedBy(memberId, lotteryId);
		if (!isCreator) throw new JandiUnauthorizedException();
	}

	private void validateDuplicateName(String name) {
		if (lotteryRepository.existsByName(name)) {
			throw new JandiDuplicatedNameException();
		}
	}
}
