package com.hk_music_cop.demo.schedule.application;

import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ScheduleServiceImplTest {

	@Autowired
	ScheduleService scheduleService;

	@Test
	void registTodo() {

	}

	@Test
	@DisplayName("주간 일정 데이터 조회")
	void getWeekTodo() {
		// given
		LocalDate date = LocalDate.now().minusWeeks(2);


		// when
		WeeklySchedule weekTodoData = scheduleService.getWeekTodo(date);

		// then
		// 비어있을 수 있는 데이터
		System.out.println(weekTodoData);
	}

	@Test
	@DisplayName("주간 일정 데이터 잔디 메시지 변환")
	void getDayTodo() {
		//given
		LocalDate date = LocalDate.of(2024, 10, 23);

		// when
		WeeklySchedule dayTodoData = scheduleService.getDayTodo(date);

		// then
		System.out.println(dayTodoData);
	}

	@Test
	void getTodayTodo() {

	}
}