package com.hk_music_cop.demo.config.advice;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

	public ErrorResponse(int code, String message) {
		timestamp = LocalDateTime.now();
	}

	private int status;
	private String message;
	private final LocalDateTime timestamp;
}
