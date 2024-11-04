package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.config.error.DuplicatedNameException;
import com.hk_music_cop.demo.config.error.NotFoundException;
import com.hk_music_cop.demo.external.jandi.application.JandiMessageConverter;
import com.hk_music_cop.demo.external.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.repository.LotteryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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

		log.info(personList.toString());

		return getRandom(personList);
	}

	// message 만드는곳으로 옮겨야함
	private JandiWebhookResponse.ConnectInfo createConnectInfoForLottery(LotteryResponse person, String imgURL) {
		return new JandiWebhookResponse.ConnectInfo("결과", "'" + person.getName() + " " + person.getPosition() + "'님 당첨되었습니다.\n축하합니다~!", imgURL);
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

	private void validateDuplicateName(String name) {
		if (lotteryRepository.existsByName(name)) {
			throw new DuplicatedNameException();
		}
	}


	@Override
	public boolean deletePerson(Long memberId, String name) {

		LotteryResponse targetLottery = validateExistByName(name);
		validateAuthorized(memberId, targetLottery.getLotteryId());

		lotteryRepository.deleteLottery(targetLottery.getLotteryId());

		return true;
	}

	@Override
	public boolean updatePerson(Long memberId, Long lotteryId, LotteryRequest lotteryRequest) {

		// 타겟 lottery 존재 확인
		validateExistById(lotteryId);

		// 권한 확인 (요청자가 생성자인지)
		validateAuthorized(memberId, lotteryId);

		// 바꿀 이름 존재 여부 확인 :: 동시성 고려?
		validateDuplicateName(lotteryRequest.getName());

		return lotteryRepository.editLottery(lotteryId, lotteryRequest) > 0;
	}

	private LotteryResponse validateExistByName(String name) {
		return lotteryRepository.findByName(name)
				.orElseThrow(() -> new NotFoundException(name));
	}

	private LotteryResponse validateExistById(Long lotteryId) {
		return lotteryRepository.findByLotteryId(lotteryId)
				.orElseThrow(NotFoundException::new);
	}

	private void validateAuthorized(Long memberId, Long lotteryId) {
		lotteryRepository.isCreatedBy(memberId, lotteryId);
	}
}
