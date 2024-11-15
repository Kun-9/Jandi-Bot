package com.hk_music_cop.demo.google_cloud.google_sheet.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.hk_music_cop.demo.global.error.exceptions.CustomApiException;
import com.hk_music_cop.demo.google_cloud.google_sheet.GoogleSheetProperties;
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

	@Override
	public List<List<String>> getSheetData(String sheetName, String start, String end, boolean isDay) {
		StringBuilder range = new StringBuilder();
		range.append(sheetName).append("!").append(start).append(":").append(end);

		log.info("range : {}", range);
		ValueRange response;

		try {
			response = sheetsService.spreadsheets().values()
					.get(googleSheetProperties.spreadsheetId(), range.toString())
					.execute();
		} catch (IOException e) {
			throw new CustomApiException();
		}

		return getSheetDataParse(isDay, response.getValues());
	}

	private List<List<String>> getSheetDataParse(Boolean isDay, List<List<Object>> sheetData) {
		List<List<String>> result = new ArrayList<>();

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

		return result;
	}
}
