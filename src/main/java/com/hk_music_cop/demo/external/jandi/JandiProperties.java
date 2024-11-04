package com.hk_music_cop.demo.external.jandi;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jandi")
public record JandiProperties(Color color, Title title) {

	// record : Getter, 생성자, 불변성 클래스 포함
	public record Color(String warningColor, String successColor) {}
	public record Title(String infoTitle, String weekScheduleTitle, String dayScheduleTitle, String lotteryTitle) {}
}


