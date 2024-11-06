package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
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

		Integer sheetNum = googleSheetProperties.calendar().sheetNumbers().get(nthWeek);
		// 해당 주의 월요일 코드
		startCode = googleSheetProperties.calendar().dayCode().get(0) + sheetNum;
		// 해당 주의 금요일 코드
		endCode = googleSheetProperties.calendar().dayCode().get(4) + sheetNum;
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
		List<List<String>> sheetDataParse = googleSheetAPI.getSheetData(sheetName, startCode, endCode, true);

		//then
		assertThat(sheetDataParse)
				.contains(
						expectedValue.get(0), expectedValue.get(1), expectedValue.get(2)
				);
	}


	@Test
	void test() {

		byte[] decode = Base64.getDecoder().decode(googleSheetProperties.config().key());

//		String key = googleSheetProperties.config().key();
		String s = new String(decode, StandardCharsets.UTF_8);

		System.out.println(s);
	}

	@Test
	void checkBase64Decoding() {
		String encoded = googleSheetProperties.config().key();
		String decoded = new String(Base64.getDecoder().decode(encoded));
		System.out.println(decoded);  // 실제 JSON 형식인지 확인
	}

}