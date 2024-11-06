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
//		String encoded = googleSheetProperties.config().key();
//		String encoded = "ewogICJ0eXBlIjogInNlcnZpY2VfYWNjb3VudCIsCiAgInByb2plY3RfaWQiOiAic3BoZXJpYy10ZW1wbGUtNDM4MjA2LXE1IiwKICAicHJpdmF0ZV9rZXlfaWQiOiAiYjAzNTgyNDBhOTI2YzNiYTcwYzRlZGJmOWFlOTQ5MWVkOTVlNzU5MSIsCiAgInByaXZhdGVfa2V5IjogIi0tLS0tQkVHSU4gUFJJVkFURSBLRVktLS0tLVxuTUlJRXZnSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NCS2d3Z2dTa0FnRUFBb0lCQVFESHYvWXc5N0hUbUlEVFxuMUVBY082cFN2TXNhNUYvc25SUmJpNWZ0ZStJanRNa0Z2S3BYTGlYYzVEYTlHWTlOaUNQaTBNV0FZYVYxakQ2UFxuTkN4bDRhYU1reWdoZk90cHhTeXcwdjFjdTl2UlZlQk5RNGpqMDIzYzRpM2FpYXVYMTQzSGFtZkZPSFNPaUtCd1xud1N0cnVVMUpDS2tiT1VxQ0k4M250SzhWeHVrV0FaMnY1ZjlvSW5TREVDbUpxZnpuQ0hEbDJLaStra3dlL1FCQ1xudHJqMWttQncvQ1RiYlZwbk9ydnVvTEZzemZxV004Mmxzc1ROUlY4MDBIUGdxZlhvejR3TWtFQU5lZ0dwRlNvWlxuYnhiRjBjMXRyNWw5NEs4RVVnWWFpSFdjS0NodDJtTFZISWp4cHlpVUVSTDVxQVpoRjNYYmlTNTd3UGdyMjRmUFxuTUVYMlR3QzlBZ01CQUFFQ2dnRUFQajFSbEVOeHZOazlkd09oTUVybEVOK3p0MzdtWnI4eEd5ODlXU21yTm0zN1xuM3haQXkyWmROTjFGTXhuV1BaWElmTUdKY0syOFZRcXNhWW1idGVrZ01KZ2Z2b09DaE5RRHoxc0d5QkRlOXZTM1xucmN0M3lXbFZSNmlRR2hLZ2pYRUVROWtqV1YvMGJYOGo0czVGM2N5QXlOdHllY0dadGtBNnhkSldaY1l6VllBQlxuTjhqek9UOFJVNTByK3lBbVRyWEpyRjZTSUdtY3VmUEdZSTNkRU91anNzbURFd05vellXb2Jrc3dQRlpaRVRyS1xuUnNTRXNkcU5EcFVUNnpQZS9OOVBQNW1wTG5EZmFJQ2RSMlNTWnFrTkZTTTFNR1ovaFl1SUkxbjJYVzNWdUpIT1xuZHVTRm1RTTdFVXNiYXlJT1M2RVAxaUlDTnZUTW12cnlLSmh3bGhPOEh3S0JnUUR3VlNvTDhJOUdSc24wemQ1M1xuKzhTYytuNndscURYVnowYXpaS2dvMURheHlwcUptZU9xeTdUYWh2cHVQZ0UxY0RwWElGc285Y0dWYVZmaTEvT1xuWGF4Q1RjdUlqWWlaUTc2bzVvZGsvaTc4WGJWZDZNbzhzSnhkVnBhQW5DM2RqeEVCT3hBTGdwMW9reFRFR2VBWVxuZmF1aEgxK2tVSWUrYW5mRUxDYkhWL1Q0MXdLQmdRRFV4WVlKdmVuRE1udTZvendJMFFNeW42OVBwRU9PTW5KeVxuelRadGtsb2l5aDNnbGVucEx2Zm9NVHRBN0UreU1zSDNFOGl0b3dNdjIyL2ZmckdmRm5oZGIwMGFiNW5oWVZIWVxuQ0VPb2IvRy95b2RBT0REQlYvQ2ljVGRPU1NHcFJVMktMNWlQUGNXb1RhNWtQdWhjbzVtZTVyTkY1VnVlVm9EblxuUTgyTXB5Szhpd0tCZ1FEU1dhZ1lsUy9CMGRtWHc4eVE5YkxMdHMvR0hjQ2ZXZ0xvZjc5empCNExyc0NuOGY4M1xudHdxZ0RrSnFHZlF5dnlnKzJER0xkZ2VReXN1WXliTk1PMTYzRmNJc1BieXVjTVhpclFzYVVRT3M3UlArSXU5SFxubVVqeXhRT3JnNGI5WWVmL1RNT3BYMlFJa0p0ZlhFcHhCU1dBTzZhSmVkYWx5aWdUY3BWcWFzakdOd0tCZ0F4aFxuNm1rS3VsMzN4Q2VhZEZYM0EvNmdPRzJrRzZKYzRUZzVKUisvVXF1UG82bDhULzZZRXlsYUpCNmxJZ1kyMmY3dFxuRWRLNUYraGpGdUdrMStWd1ExVDhzem9Fb0tuekpmWXlsTzFxaG1FcEFqSUVreUhOWWwyeHhRd3pTVmcyaGtxeVxuZG1odnFkOGZsalNUVEthSGh0c3VoZVJkY3JXR1J6NHVQZm9maCsvOUFvR0JBS2ROc296ZVFBM09RdmZqZE9ua1xueHY0cllDZ01ma1V0VzVra0hDQWdHQkdhRUQ1ejdWTXE5NDM3NEJjM1ZjUWZKSnY4R2NEU0tpZEVubXNEYlNYc1xuREttWWx4dFdSbmYra3Boc3NZUXBPTFJMZVFSNW9IVWEwVzB1R2pFbzB1N2VOYmVVb0N5eDcreHhZb1p6Yi84NFxuVjB4aEhCNjhkamxGR1pkT2U0amxNVU9zXG4tLS0tLUVORCBQUklWQVRFIEtFWS0tLS0tXG4iLAogICJjbGllbnRfZW1haWwiOiAiZG9uZ2tldW5Ac3BoZXJpYy10ZW1wbGUtNDM4MjA2LXE1LmlhbS5nc2VydmljZWFjY291bnQuY29tIiwKICAiY2xpZW50X2lkIjogIjEwNTgyMTA1MzU4NDI3NTE4OTY0MyIsCiAgImF1dGhfdXJpIjogImh0dHBzOi8vYWNjb3VudHMuZ29vZ2xlLmNvbS9vL29hdXRoMi9hdXRoIiwKICAidG9rZW5fdXJpIjogImh0dHBzOi8vb2F1dGgyLmdvb2dsZWFwaXMuY29tL3Rva2VuIiwKICAiYXV0aF9wcm92aWRlcl94NTA5X2NlcnRfdXJsIjogImh0dHBzOi8vd3d3Lmdvb2dsZWFwaXMuY29tL29hdXRoMi92MS9jZXJ0cyIsCiAgImNsaWVudF94NTA5X2NlcnRfdXJsIjogImh0dHBzOi8vd3d3Lmdvb2dsZWFwaXMuY29tL3JvYm90L3YxL21ldGFkYXRhL3g1MDkvZG9uZ2tldW4lNDBzcGhlcmljLXRlbXBsZS00MzgyMDYtcTUuaWFtLmdzZXJ2aWNlYWNjb3VudC5jb20iLAogICJ1bml2ZXJzZV9kb21haW4iOiAiZ29vZ2xlYXBpcy5jb20iCn0K";
//		String decoded = new String(Base64.getDecoder().decode(encoded));
//		System.out.println(decoded);  // 실제 JSON 형식인지 확인


	}

}