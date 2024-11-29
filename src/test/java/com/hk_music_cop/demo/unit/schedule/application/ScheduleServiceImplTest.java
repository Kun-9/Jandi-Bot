package com.hk_music_cop.demo.unit.schedule.application;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomUndefinedCommand;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.googleCloud.googleSheet.GoogleSheetProperties;
import com.hk_music_cop.demo.googleCloud.googleSheet.repository.GoogleSheetAPI;
import com.hk_music_cop.demo.schedule.application.ScheduleServiceImpl;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

	@InjectMocks
	private ScheduleServiceImpl scheduleService;

	@Mock
	private GoogleSheetProperties googleSheetProperties;

	@Mock
	private GoogleSheetAPI googleSheetAPI;

	@Test
	@DisplayName("주간 일정 조회 정상케이스")
	void getWeekTodo() {
		// given
		LocalDate testDate = LocalDate.of(2024, 11, 22);

		GoogleSheetProperties.SheetCalendar calendar = new GoogleSheetProperties.SheetCalendar(
				List.of("월", "화", "수", "목", "금"),
				List.of("A", "B", "C", "D", "E"),
				List.of(10, 12, 14, 16, 18)
		);

		when(googleSheetProperties.calendar()).thenReturn(calendar);

		List<List<String>> mockData = Arrays.asList(
				Arrays.asList("회의", "개발"),
				null,
				Arrays.asList("코딩", "테스트"),
				Arrays.asList("리뷰", "배포"),
				null
		);

		when(googleSheetAPI.getSheetData(anyString(), anyString(), anyString(), anyBoolean()))
				.thenReturn(mockData);

		// when
		WeeklySchedule result = scheduleService.getWeekTodo(testDate);

		// then
		assertThat(result).isNotNull();
		verify(googleSheetAPI).getSheetData(eq("2024.11 월간 캘린더"), eq("A16"), eq("E16"), eq(false));
	}

	@Test
	@DisplayName("일간 조회 테스트")
	void getDay() {
		// given
		LocalDate testDate = LocalDate.of(2024, 11, 22);

		GoogleSheetProperties.SheetCalendar calendar = new GoogleSheetProperties.SheetCalendar(
				List.of("월", "화", "수", "목", "금"),
				List.of("A", "B", "C", "D", "E"),
				List.of(10, 12, 14, 16, 18)
		);

		when(googleSheetProperties.calendar()).thenReturn(calendar);

		List<List<String>> mockData = List.of(
				Arrays.asList("코딩", "테스트")
		);

		when(googleSheetAPI.getSheetData(anyString(), anyString(), anyString(), anyBoolean()))
				.thenReturn(mockData);

		// when
		WeeklySchedule result = scheduleService.getDayTodo(testDate);

		// then
		assertThat(result).isNotNull().isNotNull();
		verify(googleSheetAPI).getSheetData(eq("2024.11 월간 캘린더"), eq("E16"), eq("E16"), eq(true));
	}

	@Test
	@DisplayName("주말인 경우")
	void validateHoliday() {
		// given
		LocalDate weekend = LocalDate.of(2024, 3, 23); // 토요일

		// when & then
		assertThatThrownBy(() -> scheduleService.validateHoliday(weekend))
				.isInstanceOf(CustomUndefinedCommand.class)
				.hasFieldOrPropertyWithValue("errorCode", ErrorCode.UNDEFINED_COMMAND);
	}
}