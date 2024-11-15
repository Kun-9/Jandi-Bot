package com.hk_music_cop.demo.global.security.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(String secret, long accessTokenValidityInSeconds, long refreshTokenValidityInDays) {}
