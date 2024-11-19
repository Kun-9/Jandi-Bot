package com.hk_music_cop.demo.global.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomApiException;
import com.hk_music_cop.demo.googleCloud.googleSheet.GoogleSheetProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;

@Slf4j
@Configuration
public class GoogleSheetConfig {

	@Bean
	public Sheets sheetService(GoogleSheetProperties googleSheetProperties) {

		String applicationName = googleSheetProperties.config().serviceName();
		String jsonStr = googleSheetProperties.config().key();

		// JSON 파일 복호화
		byte[] decode = Base64.getDecoder().decode(jsonStr);

		InputStream credentialsStream = new ByteArrayInputStream(decode);

		try {
			GoogleCredentials credentials = ServiceAccountCredentials.fromStream(credentialsStream)
					.createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));

			NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
			HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);

			return new Sheets.Builder(httpTransport, jsonFactory, credentialsAdapter)
					.setApplicationName(applicationName)
					.build();

		} catch (GeneralSecurityException | IOException e) {
			throw new CustomApiException("API 호출 오류");
		}
	}

}
