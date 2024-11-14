package com.hk_music_cop.demo.global.jwt.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
	private String accessToken;
	private String refreshToken;

	private String tokenType = "Bearer";
	private Long expiresIn;

	public TokenResponse(String token) {
		this.accessToken = token;
	}
}
