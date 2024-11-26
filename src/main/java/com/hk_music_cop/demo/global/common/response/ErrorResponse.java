package com.hk_music_cop.demo.global.common.response;

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

@Builder
@Getter
@RequiredArgsConstructor
public class ErrorResponse<T> {
	private final int status;           // 상태 코드
	private final String code;          // 성공,실패 코드
	private final String message;       // 응답 메시지
	private final LocalDateTime timestamp;  // 응답 시간
	private final T error;              // 에러 오브젝트


	// 응답 객체 생성 (데이터 없는 경우)
	public static ErrorResponse<Void> from(ResponseCode code) {
		return ErrorResponse.<Void>builder()
				.code(code.getCode())
				.message(code.getMessage())
				.status(code.getStatus())
				.timestamp(LocalDateTime.now())
				.build();
	}

	// CustomException 응답
	public static ErrorResponse<Void> from(CustomException e) {
		ResponseCode code = e.getResponseCode();
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
	public static ErrorResponse<Void> error(ResponseCode code, String message) {
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
