package com.hk_music_cop.demo.external.jandi.application;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class JandiMessageFactoryImplTest {

	@Autowired
	JandiMessageFactory jandiMessageFactory;

	@Test
	void scheduleWeekMessage() {
		//
		LocalDate date = LocalDate.now().minusWeeks(3);

		//
		JSONObject message = jandiMessageFactory.scheduleWeekMessage(date);

		//
		System.out.println(message);
	}

	@Test
	void scheduleDayMessage() {
		//
		LocalDate date = LocalDate.of(2024, 10, 1);

		//
		JSONObject message = jandiMessageFactory.scheduleDayMessage(date);

		//
		System.out.println(message);

	}

	@Test
	void chooseLotteryMessage() {
		//


		//
		JSONObject message = jandiMessageFactory.chooseLotteryMessage(null);


		//

	}
}