package com.hk_music_cop.demo.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hk_music_cop.demo.global.common.error.ValidationError;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Null이 아닌 요소만 Json 으로 응답
public class ApiResponse<T> {
	private final int status;           // 상태 코드
	private final String code;          // 성공,실패 코드
	private final String message;       // 응답 메시지
	private final T data;              // 실제 데이터 (generic)
	private final LocalDateTime timestamp;  // 응답 시간
	private final T error;

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
