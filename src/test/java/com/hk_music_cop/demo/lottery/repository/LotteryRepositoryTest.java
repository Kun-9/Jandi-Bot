package com.hk_music_cop.demo.lottery.repository;

import com.hk_music_cop.demo.lottery.dto.request.LotteryRequest;
import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LotteryRepositoryTest {

	@Autowired
	LotteryRepository lotteryRepository;

	LotteryRequest testObject;
	Long testId;

	@BeforeEach
	void setup() {
		testObject = new LotteryRequest(5L, "test", "test");
		testId = lotteryRepository.createLottery(testObject);
	}

	@Test
	void createLottery() {
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
		LotteryResponse findLottery = lotteryRepository.findByLotteryId(testId);


		//
		assertThat(findLottery.getLotteryId()).isEqualTo(testId);
		System.out.println(testId);
	}

	@Test
	void findByName() {
		//


		//
		LotteryResponse findLottery = lotteryRepository.findByName(testObject.getName());


		//
		assertThat(findLottery.getLotteryId()).isEqualTo(testId);
	}

	@Test
	void editLottery() {
		//
		LotteryRequest editLottery = new LotteryRequest(null, "edit", "editPos");


		//
		int i = lotteryRepository.editLottery(testId, editLottery);
		LotteryResponse findEditObject = lotteryRepository.findByLotteryId(testId);


		assertThat(i).isEqualTo(1);
		assertThat(findEditObject)
				.satisfies(object -> {
					assertThat(object.getName()).isEqualTo(editLottery.getName());
					assertThat(object.getPosition()).isEqualTo(editLottery.getPosition());
				});
	}

	@Test
	void deleteLottery() {
		//

		//
		int i = lotteryRepository.deleteLottery(testId);
		LotteryResponse findLottery = lotteryRepository.findByLotteryId(testId);

		//
		assertThat(i).isEqualTo(1);
		assertThat(findLottery).isNull();
	}

	@Test
	void findAll() {
		//
//		testObject1 = new LotteryRequest(5L, "test", "test");
//		testObject2 = new LotteryRequest(5L, "test", "test");
//		testId = lotteryRepository.createLottery(testObject);

		List<LotteryRequest> requests = Arrays.asList(
				new LotteryRequest(5L, "test1", "testPos1"),
				new LotteryRequest(5L, "test2", "testPos2"),
				new LotteryRequest(5L, "test3", "testPos3")
		);

		requests.forEach(lotteryRepository::createLottery);

		List<Tuple> expectedTuple = requests.stream().map(
				data -> tuple(data.getName(), data.getPosition())
		).toList();


		//
		List<LotteryResponse> findLotteryList = lotteryRepository.findAll();

		//
		assertThat(findLotteryList)
				.extracting("name", "position")
				.contains(
						expectedTuple.toArray(new Tuple[0])
				);

	}
}