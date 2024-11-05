package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Component
public class GoogleSheetConfig {

	private static final String APPLICATION_NAME = "Google Sheets API";
	private static Sheets sheetsService;

	public GoogleSheetConfig() throws IOException, GeneralSecurityException {
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

	public Sheets getSheetsService() {
		return sheetsService;
	}
}
