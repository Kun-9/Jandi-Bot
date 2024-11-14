package com.hk_music_cop.demo.global.config.advice.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
	private final int status;
	private final String message;
	private final LocalDateTime timestamp;

	public ErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
		timestamp = LocalDateTime.now();
	}
}
