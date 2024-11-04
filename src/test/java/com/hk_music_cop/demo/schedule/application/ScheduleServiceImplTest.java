package com.hk_music_cop.demo.schedule.application;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ScheduleServiceImplTest {

	@Autowired
	ScheduleService scheduleService;

	@Test
	void registTodo() {

	}

	@Test
	@DisplayName("주간 일정 데이터 조회")
	void getWeekTodoData() {
		LocalDate date = LocalDate.now().minusWeeks(2);

		// given
		String title = "testTitle";
		String color = "#2E2A2E";


		// when
		List<List<String>> weekTodoData = scheduleService.getWeekTodoData(title, color, date);

		// then
		// 비어있을 수 있는 데이터
		System.out.println(weekTodoData);
	}

	@Test
	@DisplayName("주간 일정 데이터 잔디 메시지 변환")
	void getDayTodoData() {
		LocalDate date = LocalDate.now().minusWeeks(2);

		// given
		String title = "testTitle";
		String color = "#2E2A2E";

		// when
		List<List<String>> dayTodoData = scheduleService.getDayTodoData(title, color, date);

		// then
		System.out.println(dayTodoData);
	}

	@Test
	void getTodayTodo() {

	}
}