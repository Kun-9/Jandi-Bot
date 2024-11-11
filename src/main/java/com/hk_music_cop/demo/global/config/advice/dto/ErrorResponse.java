package com.hk_music_cop.demo.global.config.advice.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

	public ErrorResponse(int status, String message) {
		timestamp = LocalDateTime.now();
		this.status = status;
		this.message = message;

	}

	private final int status;
	private final String message;
	private final LocalDateTime timestamp;
}
