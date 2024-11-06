package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.hk_music_cop.demo.global.error.common.CustomApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Collections;

@Slf4j
@Configuration
public class GoogleSheetConfig {

	@Bean
	public Sheets sheetService(GoogleSheetProperties googleSheetProperties) {

		String jsonStr = googleSheetProperties.config().key();
		log.info("Received JSON: {}", jsonStr);
		byte[] decode = Base64.getDecoder().decode(jsonStr);

		log.info("decode JSON : {}", new String(decode, StandardCharsets.UTF_8));

		InputStream credentialsStream = new ByteArrayInputStream(
				decode
		);

		GoogleCredentials credentials;
		try {
			credentials = ServiceAccountCredentials.fromStream(credentialsStream)
					.createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));


			return new Sheets.Builder(
					GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(),
					new HttpCredentialsAdapter(credentials))
					.setApplicationName(googleSheetProperties.config().serviceName())
					.build();
		} catch (GeneralSecurityException | IOException e) {
			throw new CustomApiException("API 호출 오류");
		}
	}
}
