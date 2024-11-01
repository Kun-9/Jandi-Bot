package com.hk_music_cop.demo.external.google_cloud.google_sheet;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;


@Getter @Setter @ConfigurationProperties(prefix = "google-sheet") @Component
public class GoogleSheetProperties {

	private String spreadsheetId;
	private SheetCalendar calendar;

	@Getter
	@Setter
	public static class SheetCalendar {
		private List<String> dayList;
		private List<String> dayCode;
		private List<Integer> sheetNumbers;
	}
}
