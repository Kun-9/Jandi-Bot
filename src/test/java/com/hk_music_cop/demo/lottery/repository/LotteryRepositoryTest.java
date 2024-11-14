package com.hk_music_cop.demo.lottery.repository;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class LotteryRepositoryTest {

	@Autowired
	LotteryRepository lotteryRepository;

	LotteryRequest testObject;
	Long testLotteryId;

	@BeforeEach
	void setup() {
		testObject = new LotteryRequest(5L, "test", "test");
		testLotteryId = lotteryRepository.createLottery(testObject);
	}

	@Test
	void createLottery() {
		//
		LotteryRequest testObject1 = new LotteryRequest(5L, "test", "test");

		//
		Long lotteryId = lotteryRepository.createLottery(testObject1);

		//
		assertThat(lotteryId).isNotEqualTo(0);
	}

	@Test
	void findByLotteryId() {
		//


		//
		LotteryResponse findLottery = lotteryRepository.findByLotteryId(testLotteryId).get();


		//
		assertThat(findLottery.getLotteryId()).isEqualTo(testLotteryId);
		System.out.println(testLotteryId);
	}

	@Test
	void findByName() {
		//


		//
		LotteryResponse findLottery = lotteryRepository.findByName(testObject.getLotteryName()).get();


		//
		assertThat(findLottery.getLotteryId()).isEqualTo(testLotteryId);
	}

	@Test
	void editLottery() {
		//
		LotteryRequest editLottery = new LotteryRequest(null, "edit", "editPos");


		//
		int i = lotteryRepository.editLottery(testLotteryId, editLottery);
		LotteryResponse findEditObject = lotteryRepository.findByLotteryId(testLotteryId).get();


		assertThat(i).isEqualTo(1);
		assertThat(findEditObject)
				.satisfies(object -> {
					assertThat(object.getLotteryName()).isEqualTo(editLottery.getLotteryName());
					assertThat(object.getPosition()).isEqualTo(editLottery.getPosition());
				});
	}

	@Test
	void deleteLottery() {
		//


		//
		int i = lotteryRepository.deleteLottery(testLotteryId);
		Optional<LotteryResponse> findLottery = lotteryRepository.findByLotteryId(testLotteryId);

		//
		assertThat(i).isEqualTo(1);
		assertThat(findLottery.isEmpty()).isTrue();
	}

	@Test
	void findAll() {
		//
		List<LotteryRequest> requests = Arrays.asList(
				new LotteryRequest(5L, "test1", "testPos1"),
				new LotteryRequest(5L, "test2", "testPos2"),
				new LotteryRequest(5L, "test3", "testPos3")
		);

		requests.forEach(lotteryRepository::createLottery);

		List<Tuple> expectedTuple = requests.stream().map(
				data -> tuple(data.getLotteryName(), data.getPosition())
		).toList();


		//
		List<LotteryResponse> findLotteryList = lotteryRepository.findAll();

		//
		assertThat(findLotteryList)
				.extracting("name", "position")
				.contains(
						expectedTuple.toArray(new Tuple[0])
				);

		System.out.println(findLotteryList);
	}

	@Test
	void existsByName() {
		//
		String name = testObject.getLotteryName();

		//
		boolean isExistExTrue = lotteryRepository.existsByName(name);
		boolean isExistExFalse = lotteryRepository.existsByName(UUID.randomUUID().toString().substring(0, 8));

		//
		assertThat(isExistExTrue).isTrue();
		assertThat(isExistExFalse).isFalse();
	}

	@DisplayName("생성자 이름 매칭 성공")
	@Test
	void isCreatedBy_ValidRequest_Success() {
		//


		//
		boolean result = lotteryRepository.isCreatedBy(testObject.getMemberId(), testLotteryId);

		//
		assertThat(result).isTrue();
	}

	@DisplayName("생성자 이름 매칭 실패")
	@Test
	void isCreatedBy_ValidRequest_Failed() {
		//


		//
		boolean result = lotteryRepository.isCreatedBy(999999L, testLotteryId);

		//
		assertThat(result).isFalse();
	}
}