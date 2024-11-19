//package com.hk_music_cop.demo.global.error.dto;
//
//import java.time.LocalDateTime;
//
//public record ErrorResponse(int status, String message, LocalDateTime timestamp) {
//
//	public static ErrorResponse of(int status, String message) {
//		LocalDateTime timestamp = LocalDateTime.now();
//
//		return new ErrorResponse(status, message, timestamp);
//	}
//}
