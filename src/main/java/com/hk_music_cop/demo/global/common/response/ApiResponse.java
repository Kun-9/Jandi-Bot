package com.hk_music_cop.demo.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


/**
 * @param status    상태코드
 * @param code      성공, 실패 코드
 * @param message   응답 메시지
 * @param timestamp 응답 시간
 * @param data      응답 데이터
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Null이 아닌 요소만 Json 으로 응답
public record ApiResponse<T> (int status, String code, String message, LocalDateTime timestamp, T data) {

	// 응답 객체 생성 (데이터 있는 경우)
	public static <T> ApiResponse<T> of(ResponseCode code, T data) {
		return ApiResponse.<T>builder()
				.code(code.getCode())
				.message(code.getMessage())
				.data(data)
				.status(code.getStatus())
				.timestamp(LocalDateTime.now())
				.build();
	}

	// 응답 객체 생성 (데이터 없는 경우)
	public static ApiResponse<Void> from(ResponseCode code) {
		return ApiResponse.<Void>builder()
				.code(code.getCode())
				.message(code.getMessage())
				.status(code.getStatus())
				.timestamp(LocalDateTime.now())
				.build();
	}

	public ApiResponse<Void> withMessage(String message) {
		return ApiResponse.<Void>builder()
				.code(this.code)
				.message(message)
				.status(this.status)
				.timestamp(this.timestamp)
				.build();
	}

	// 메시지 추가
	public static ApiResponse<Void> error(ResponseCode code, String message) {
		return ApiResponse.<Void>builder()
				.code(code.getCode())
				.status(code.getStatus())
				.message(code.getMessage() + " : " + message)
				.timestamp(LocalDateTime.now())
				.build();
	}
}
