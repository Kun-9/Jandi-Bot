package com.hk_music_cop.demo.external.jandi.application;

import com.hk_music_cop.demo.schedule.application.ScheduleService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JandiMessageServiceImplTest {

	@Autowired
	JandiMessageService jandiMessageService;

	@Test
	void scheduleWeekMessage() {
		//
		LocalDate date = LocalDate.now().minusWeeks(3);

		//
		JSONObject message = jandiMessageService.scheduleWeekMessage(date);

		//
		System.out.println(message);
	}

	@Test
	void scheduleDayMessage() {
		//
		LocalDate date = LocalDate.of(2024, 10, 1);

		//
		JSONObject message = jandiMessageService.scheduleDayMessage(date);

		//
		System.out.println(message);

	}

	@Test
	void lotteryMessage() {
		//


		//
		JSONObject message = jandiMessageService.lotteryMessage(null);


		//

	}
}