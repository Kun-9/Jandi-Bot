package com.hk_music_cop.demo.global.security.jwt.dto;

import java.util.Date;

public record TokenResponse(String accessToken, String refreshToken, Date expiresIn) {
	public static TokenResponse of(String accessToken, String refreshToken, Date expiresIn) {
		return new TokenResponse("Bearer " + accessToken, refreshToken, expiresIn);
	}
}
