package com.hk_music_cop.demo.global.config;

import com.hk_music_cop.demo.external.google_cloud.google_sheet.GoogleSheetProperties;
import com.hk_music_cop.demo.external.jandi.JandiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JandiProperties.class, GoogleSheetProperties.class})
public class AppConfig {
}
