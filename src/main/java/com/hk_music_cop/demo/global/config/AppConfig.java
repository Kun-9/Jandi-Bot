package com.hk_music_cop.demo.global.config;

import com.google.api.services.sheets.v4.Sheets;
import com.hk_music_cop.demo.external.google_cloud.google_sheet.GoogleSheetConfig;
import com.hk_music_cop.demo.external.google_cloud.google_sheet.GoogleSheetProperties;
import com.hk_music_cop.demo.jandi.JandiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
@EnableConfigurationProperties({JandiProperties.class, GoogleSheetProperties.class})
public class AppConfig {

	@Bean
	public Sheets sheetService() throws GeneralSecurityException, IOException {
		GoogleSheetConfig googleSheetConfig = new GoogleSheetConfig();
		return googleSheetConfig.getSheetsService();
	}
}
