package com.hk_music_cop.demo.lottery.application;

import com.hk_music_cop.demo.lottery.dto.response.LotteryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LotteryServiceImplTest {

	@Autowired
	LotteryService lotteryService;

	private final static String title = "testTitle";
	private final static String color = "#FF6188";
	private final static String imgURL = null;

	@Test
	void chooseLotteryWinner() {


		LotteryResponse lotteryResponse = lotteryService.chooseLotteryWinner(title, color, imgURL);



	}

	@Test
	void registPerson() {

	}

	@Test
	void deletePerson() {
	}

	@Test
	void updatePerson() {
	}
}