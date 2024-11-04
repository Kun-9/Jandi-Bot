package com.hk_music_cop.demo.external.jandi;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter @ConfigurationProperties(prefix = "jandi") @ToString @NoArgsConstructor @AllArgsConstructor
public class JandiProperties {

	private Color color;
	private Title title;

	@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
	public static class Color {
		private String warningColor;
		private String successColor;
	}

	@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
	public static class Title {
		private String infoTitle;
		private String weekScheduleTitle;
		private String dayScheduleTitle;
		private String lotteryTitle;
	}
}
