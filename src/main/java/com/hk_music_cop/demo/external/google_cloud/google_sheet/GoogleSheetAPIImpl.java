package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.hk_music_cop.demo.global.error.common.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleSheetAPIImpl implements GoogleSheetAPI {
	private final GoogleSheetProperties googleSheetProperties;
	private final Sheets sheetsService;

	private List<List<Object>> getSheetData(String sheetName, String start, String end) throws IOException {
		StringBuilder range = new StringBuilder();
		range.append(sheetName).append("!").append(start).append(":").append(end);

		log.info("range : {}", range);

		ValueRange response = sheetsService.spreadsheets().values()
				.get(googleSheetProperties.spreadsheetId(), range.toString())
				.execute();

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
			throw new CustomApiException("입력 값을 확인해주세요.");
		}

		return result;
	}
}
