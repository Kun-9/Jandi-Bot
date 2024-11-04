package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Getter @Setter @ConfigurationProperties(prefix = "google-sheet") @NoArgsConstructor @AllArgsConstructor
public class GoogleSheetProperties {

	private String spreadsheetId;
	private SheetCalendar calendar;

	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class SheetCalendar {
		private List<String> dayList;
		private List<String> dayCode;
		private List<Integer> sheetNumbers;
	}
}
