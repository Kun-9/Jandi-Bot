package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class GoogleSheetAPIImplTest {

	@Autowired
	GoogleSheetAPI googleSheetAPI;

	@Autowired
	GoogleSheetProperties googleSheetProperties;

	String sheetName;
	String startCode;
	String endCode;

	@BeforeEach
	void setup() {
		int year = 2024;
		int month = 10;
		int nthWeek = 3;

		sheetName = year + "." + month + " " + "월간 캘린더";

		Integer sheetNum = googleSheetProperties.getCalendar().getSheetNumbers().get(nthWeek);
		// 해당 주의 월요일 코드
		startCode = googleSheetProperties.getCalendar().getDayCode().get(0) + sheetNum;
		// 해당 주의 금요일 코드
		endCode = googleSheetProperties.getCalendar().getDayCode().get(4) + sheetNum;
	}

	@Test
	void getSheetDataParse() {
		// given
		// setup 에서 값 세팅
		List<List<String>> expectedValue = Arrays.asList(
				Arrays.asList("라온 미팅"),
				Arrays.asList("포인정보미팅", "신탁회계팀(PM 14:00)"),
				Arrays.asList("요구사항정의 및 프로세스 내부 확정")
		);


		// when
		// 2024 10월 4번째 주 일정 조회
		List<List<String>> sheetDataParse = googleSheetAPI.getSheetDataParse(sheetName, startCode, endCode, true);

		//then
		assertThat(sheetDataParse)
				.contains(
						expectedValue.get(0), expectedValue.get(1), expectedValue.get(2)
				);
	}
}