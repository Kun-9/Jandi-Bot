package com.hk_music_cop.demo.schedule.application;

import com.hk_music_cop.demo.external.google_cloud.google_sheet.GoogleSheetAPI;
import com.hk_music_cop.demo.external.google_cloud.google_sheet.GoogleSheetProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private final GoogleSheetProperties googleSheetProperties;
	private final GoogleSheetAPI googleSheetAPI;

	@Override
	public JSONObject registTodo(String name, String todo) {
		return null;
	}

	@Override
	public List<List<String>> getWeekTodoData(String title, String color, LocalDate date) {

		int year = date.getYear();
		int month = date.getMonthValue();

		String sheetName = generateSheetName(year, month).toString();

		// 오늘이 몇번째 주인지 구하기
		int nthWeek = getNthWeek(date) - 1;

		Integer sheetNum = googleSheetProperties.calendar().sheetNumbers().get(nthWeek);

		// 해당 주의 월요일 코드
		String startCode = googleSheetProperties.calendar().dayCode().get(0) + sheetNum;

		// 해당 주의 금요일 코드
		String endCode = googleSheetProperties.calendar().dayCode().get(4) + sheetNum;

		return googleSheetAPI.getSheetDataParse(sheetName, startCode, endCode, true);
	}

	@Override
	public List<List<String>> getDayTodoData(String title, String color, LocalDate date) {

		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfWeek().getValue();

		String sheetName = generateSheetName(year, month).toString();

		// 오늘이 몇번째 주인지 구하기
		int nthWeek = getNthWeek(date) - 1;

		String code = googleSheetProperties.calendar().dayCode().get(day - 1) + googleSheetProperties.calendar().sheetNumbers().get(nthWeek);
		return googleSheetAPI.getSheetDataParse(sheetName, code, code, true);
	}

	private static StringBuilder generateSheetName(int year, int month) {
		StringBuilder sheetName = new StringBuilder();
		sheetName.append(year).append(".").append(month).append(" ").append("월간 캘린더");
		return sheetName;
	}

	// 날짜를 기준으로 몇번째 주인지 계산
	private int getNthWeek(LocalDate today) {
		// WeekFields 인스턴스 생성 (주의 시작을 일요일로 설정)
		WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1);

		return today.get(weekFields.weekOfMonth());
	}

	private LocalDate getMonday(LocalDate now) {
		return now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	}
}
