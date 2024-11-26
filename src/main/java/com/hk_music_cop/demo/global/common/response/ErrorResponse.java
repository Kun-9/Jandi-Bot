package com.hk_music_cop.demo.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hk_music_cop.demo.global.common.error.ValidationError;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @param status    상태 코드
 * @param code      성공,실패 코드
 * @param message   응답 메시지
 * @param timestamp 응답 시간
 * @param error     에러 데이터
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse<T>(int status, String code, String message, LocalDateTime timestamp, T error) {
	// 응답 객체 생성 (데이터 없는 경우)
	public static ErrorResponse<Void> from(ErrorCode code) {
		return ErrorResponse.<Void>builder()
				.code(code.getCode())
				.message(code.getMessage())
				.status(code.getStatus())
				.timestamp(LocalDateTime.now())
				.build();
	}

	// CustomException 응답
	public static ErrorResponse<Void> from(CustomException e) {
		ErrorCode code = e.getErrorCode();
		return ErrorResponse.<Void>builder()
				.code(code.getCode())
				.message(e.getMessage())
				.status(code.getStatus())
				.timestamp(LocalDateTime.now())
				.build();
	}

	public ErrorResponse<Void> withMessage(String message) {
		return ErrorResponse.<Void>builder()
				.code(this.code)
				.message(message)
				.status(this.status)
				.timestamp(this.timestamp)
				.build();
	}

	// 메시지 추가
	public static ErrorResponse<Void> error(ErrorCode code, String message) {
		return ErrorResponse.<Void>builder()
				.code(code.getCode())
				.status(code.getStatus())
				.message(code.getMessage() + " : " + message)
				.timestamp(LocalDateTime.now())
				.build();
	}

	public static ErrorResponse<List<ValidationError>> validationError(MethodArgumentNotValidException e) {

		BindingResult bindingResult = e.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		List<ValidationError> errors = ValidationError.from(fieldErrors);

		return ErrorResponse.<List<ValidationError>>builder()
				.status(e.getStatusCode().value())
				.error(errors)
				.timestamp(LocalDateTime.now())
				.build();
	}
}
