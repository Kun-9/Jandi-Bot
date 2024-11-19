package com.hk_music_cop.demo.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Null이 아닌 요소만 Json 으로 응답
public class ApiResponse<T> {
	private final int status;           // 상태 코드
	private final String code;          // 성공,실패 코드
	private final String message;       // 응답 메시지
	private final T data;              // 실제 데이터 (generic)
	private final LocalDateTime timestamp;  // 응답 시간

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
	public static <T> ApiResponse<T> from(ResponseCode code) {
		return ApiResponse.<T>builder()
				.code(code.getCode())
				.message(code.getMessage())
				.status(code.getStatus())
				.timestamp(LocalDateTime.now())
				.build();
	}

	// CustomException 응답
	public static <T> ApiResponse<T> from(CustomException e) {
		ResponseCode code = e.getResponseCode();
		return ApiResponse.<T>builder()
				.code(code.getCode())
				.message(e.getMessage())
				.status(code.getStatus())
				.timestamp(LocalDateTime.now())
				.build();
	}


	public ApiResponse<T> withMessage(String message) {
		return ApiResponse.<T>builder()
				.code(this.code)
				.message(message)
				.status(this.status)
				.timestamp(this.timestamp)
				.build();
	}

	// 메시지 추가
	public static <T> ApiResponse<T> error(ResponseCode code, String message) {
		return ApiResponse.<T>builder()
				.code(code.getCode())
				.status(code.getStatus())
				.message(code.getMessage() + " : " + message)
				.timestamp(LocalDateTime.now())
				.build();
	}
}
