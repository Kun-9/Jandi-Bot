package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "google-sheet")
public record GoogleSheetProperties(String spreadsheetId, SheetCalendar calendar, Config config) {
	public record SheetCalendar(List<String> dayList, List<String> dayCode, List<Integer> sheetNumbers) {}
	public record Config(String key, String serviceName) {}
}


