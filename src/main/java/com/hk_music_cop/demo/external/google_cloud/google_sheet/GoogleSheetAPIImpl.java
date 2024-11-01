package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class GoogleSheetAPIImpl {

	private final GoogleSheetProperties googleSheetProperties;
	private static final String APPLICATION_NAME = "Google Sheets API";
	private static Sheets sheetsService;

	public GoogleSheetAPIImpl(GoogleSheetProperties googleSheetProperties) throws IOException, GeneralSecurityException {
		this.googleSheetProperties = googleSheetProperties;
		initializeSheetsService();
	}

	private void initializeSheetsService() throws IOException, GeneralSecurityException {
		InputStream credentialsStream = getClass().getResourceAsStream("/sheet.json");
		if (credentialsStream == null) {
			log.error("경로 오류");
			throw new FileNotFoundException("이 경로에서 파일을 찾을 수 없습니다.");
		}

		GoogleCredentials credentials = ServiceAccountCredentials.fromStream(credentialsStream)
				.createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));


//		GoogleCredentials credentials = ServiceAccountCredentials.fromStream(
//						new FileInputStream("/Users/kun/Downloads/sheet.json"));

		sheetsService = new Sheets.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(),
				new HttpCredentialsAdapter(credentials))
				.setApplicationName(APPLICATION_NAME)
				.build();
	}

	public List<List<Object>> getSheetData(String sheetName, String start, String end) throws IOException {
		StringBuilder range = new StringBuilder();
		range.append(sheetName).append("!").append(start).append(":").append(end);

		log.info("range : {}", range);

		ValueRange response = sheetsService.spreadsheets().values()
				.get(googleSheetProperties.getSpreadsheetId(), range.toString())
				.execute();

		log.info("response : {}", response.getValues());
		return response.getValues();
	}

	public List<List<String>> getSheetDataParse(String sheetName, String startCode, String endCode, Boolean isDay) {
		List<List<String>> result = new ArrayList<>();

		try {
			List<List<Object>> sheetData = getSheetData(sheetName, startCode, endCode);

			if (sheetData == null) return result;

			for (List<Object> row : sheetData) {
				for (Object o : row) {
					// 빈 칸인지 확인 후 빈칸이 아니면 해당 일에 줄바꿈을 구분자로 task 입력
					// 빈칸이면 null로 대체
					if (!String.valueOf(o).trim().isEmpty()) {

						List<String> split = List.of(String.valueOf(o).split("\n"));
						ArrayList<String> dayTodo = new ArrayList<>(split);

						result.add(dayTodo);
					} else {
						if (Boolean.FALSE.equals(isDay)) result.add(null);
					}
				}
			}
		} catch (Exception e) {
			log.error("구글 시트 요청 실패");
			e.printStackTrace();
		}

		return result;
	}

}
