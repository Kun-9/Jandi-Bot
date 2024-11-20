package com.hk_music_cop.demo.schedule.application;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomUndefinedCommand;
import com.hk_music_cop.demo.googleCloud.googleSheet.GoogleSheetProperties;
import com.hk_music_cop.demo.googleCloud.googleSheet.repository.GoogleSheetAPI;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

	@InjectMocks
	private ScheduleServiceImpl scheduleService;

	@Mock
	private GoogleSheetProperties googleSheetProperties;

	@Mock
	private GoogleSheetAPI googleSheetAPI;

	@Test
	void getWeekTodo_정상케이스() {
		// given
		LocalDate testDate = LocalDate.of(2024, 3, 20);

		GoogleSheetProperties.SheetCalendar mockCalendar = mock(GoogleSheetProperties.SheetCalendar.class);
		when(googleSheetProperties.calendar()).thenReturn(mockCalendar);
		when(mockCalendar.sheetNumbers()).thenReturn(Arrays.asList(1, 2, 3, 4, 5));
		when(mockCalendar.dayCode()).thenReturn(Arrays.asList("A", "B", "C", "D", "E"));

		List<List<String>> mockData = Arrays.asList(
				Arrays.asList("월", "회의", "개발"),
				Arrays.asList("화", "코딩", "테스트"),
				Arrays.asList("수", "리뷰", "배포")
		);

		when(googleSheetAPI.getSheetData(anyString(), anyString(), anyString(), anyBoolean()))
				.thenReturn(mockData);

		// when
		WeeklySchedule result = scheduleService.getWeekTodo(testDate);

		// then
		assertThat(result).isNotNull();
		verify(googleSheetAPI).getSheetData(eq("2024.03 월간 캘린더"), eq("A4"), eq("E4"), eq(false));
	}

	@Test
	void validateHoliday_주말인경우() {
		// given
		LocalDate weekend = LocalDate.of(2024, 3, 23); // 토요일

		// when & then
		assertThatThrownBy(() -> scheduleService.validateHoliday(weekend))
				.isInstanceOf(CustomUndefinedCommand.class)
				.hasMessage("적절하지 않은 명령어입니다. : 주말은 쉬는날입니다.");
	}
}