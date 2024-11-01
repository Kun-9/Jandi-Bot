package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public interface GoogleSheetAPI {
	void initializeSheetsService() throws IOException, GeneralSecurityException;

	/**
	 * 구글시트에서 데이터 가져옴
	 * ',' 이나 '\n' 등 포함
	 * @param sheetName
	 * @param start
	 * @param end
	 * @return 있는 그대로 가져온 List
	 * @throws IOException
	 */
	List<List<Object>> getSheetData(String sheetName, String start, String end) throws IOException;

	/**
	 * 구글시트에서 가져온 데이터를 파싱
	 * ',' '\n' 공백 칸 등을 삭제
	 * @param sheetName
	 * @param startCode
	 * @param endCode
	 * @param isDay
	 * @return 정제된 날짜별 Todo List
	 */
	List<List<String>> getSheetDataParse(String sheetName, String startCode, String endCode, Boolean isDay);
}
