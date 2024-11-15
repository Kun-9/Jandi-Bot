package com.hk_music_cop.demo.global.config;

import com.hk_music_cop.demo.google_cloud.google_sheet.GoogleSheetProperties;
import com.hk_music_cop.demo.global.jwt.JwtProperties;
import com.hk_music_cop.demo.jandi.JandiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties({JandiProperties.class, GoogleSheetProperties.class, JwtProperties.class})
public class AppConfig {

}
