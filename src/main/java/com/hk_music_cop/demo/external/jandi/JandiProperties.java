package com.hk_music_cop.demo.external.jandi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter @Setter @ConfigurationProperties(prefix = "jandi") @Component @ToString
public class JandiProperties {

	private Color color;

	@Getter @Setter @ToString
	public static class Color {
		private String warningColor;
		private String successColor;
	}
}
