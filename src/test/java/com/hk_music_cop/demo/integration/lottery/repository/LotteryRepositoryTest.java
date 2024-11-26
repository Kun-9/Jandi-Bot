package com.hk_music_cop.demo.integration.lottery.repository;

import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryDetailResponse;
import com.hk_music_cop.demo.lottery.repository.LotteryRepository;
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
		// given
		LotteryRequest testObject1 = new LotteryRequest(5L, "test", "test");

		// when
		Long lotteryId = lotteryRepository.createLottery(testObject1);

		// then
		assertThat(lotteryId).isNotEqualTo(0);
	}

	@Test
	void findByLotteryId() {
		// when
		LotteryDetailResponse findLottery = lotteryRepository.findByLotteryId(testLotteryId).get();

		// then
		assertThat(findLottery.getLotteryId()).isEqualTo(testLotteryId);
		System.out.println(testLotteryId);
	}

	@Test
	void editLottery() {
		// given
		LotteryResponse editLottery = new LotteryResponse("edit", "이사");

		// when & then
		int i = lotteryRepository.editLottery(testLotteryId, LotteryResponse.from(editLottery));
		LotteryDetailResponse findEditObject = lotteryRepository.findByLotteryId(testLotteryId).get();

		assertThat(i).isEqualTo(1);
		assertThat(findEditObject)
				.satisfies(object -> {
					assertThat(object.getLotteryName()).isEqualTo(editLottery.lotteryName());
					assertThat(object.getPosition()).isEqualTo(editLottery.position());
				});
	}

	@Test
	void deleteLottery() {
		// when
		int i = lotteryRepository.deleteLottery(testLotteryId);
		Optional<LotteryDetailResponse> findLottery = lotteryRepository.findByLotteryId(testLotteryId);

		// then
		assertThat(i).isEqualTo(1);
		assertThat(findLottery.isEmpty()).isTrue();
	}

	@Test
	void findAll() {
		// given
		List<LotteryRequest> requests = Arrays.asList(
				new LotteryRequest(5L, "testabcl", "주임"),
				new LotteryRequest(5L, "testabcll", "이사"),
				new LotteryRequest(5L, "testabclll", "과장")
		);

		requests.forEach(lotteryRepository::createLottery);

		List<Tuple> expectedTuple = requests.stream().map(
				data -> tuple(data.lotteryName(), data.position())
		).toList();

		// when
		List<LotteryDetailResponse> findLotteryList = lotteryRepository.findAll();

		// then
		assertThat(findLotteryList)
				.extracting("lotteryName", "position")
				.contains(
						expectedTuple.toArray(new Tuple[0])
				);

		System.out.println(findLotteryList);
	}

	@Test
	void existsByName() {
		// given
		String name = testObject.lotteryName();

		// when
		boolean isExistExTrue = lotteryRepository.existsByName(name);
		boolean isExistExFalse = lotteryRepository.existsByName(UUID.randomUUID().toString().substring(0, 8));

		// then
		assertThat(isExistExTrue).isTrue();
		assertThat(isExistExFalse).isFalse();
	}

	@DisplayName("생성자 이름 매칭 성공")
	@Test
	void isCreatedBy_ValidRequest_Success() {
		// when
		boolean result = lotteryRepository.isCreatedBy(testObject.memberId(), testLotteryId);

		// then
		assertThat(result).isTrue();
	}

	@DisplayName("생성자 이름 매칭 실패")
	@Test
	void isCreatedBy_ValidRequest_Failed() {
		// when
		boolean result = lotteryRepository.isCreatedBy(999999L, testLotteryId);

		// then
		assertThat(result).isFalse();
	}
}