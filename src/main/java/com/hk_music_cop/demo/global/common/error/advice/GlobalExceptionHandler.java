package com.hk_music_cop.demo.global.common.error.advice;

import com.hk_music_cop.demo.global.common.error.ValidationError;
import com.hk_music_cop.demo.global.common.response.ErrorResponse;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.global.common.error.ErrorHandler;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.sql.SQLException;
import java.util.List;


@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	private final ErrorHandler errorHandler;

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse<?>> handleApiCustomException(CustomException e) {

		ErrorResponse<?> apiResponse = errorHandler.handleCustomException(e);

		return ResponseEntity
				.status(apiResponse.status())
				.body(apiResponse);
	}

	// SQL, DB 오류
	@Order(1)
	@ExceptionHandler({SQLException.class, DataAccessException.class})
	public ResponseEntity<ErrorResponse<?>> handleApiDBException(SQLException e) {

		ErrorResponse<?> response = errorHandler.handleException(e, ErrorCode.DATABASE_ERROR);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}

	// URL 경로, 쿼리파라미터 타입 변환 실패
	@Order(1)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse<?>> handleApiParamException(HttpMessageNotReadableException e) {

		ErrorResponse<?> apiResponse = errorHandler.handleException(e, ErrorCode.UNDEFINED_COMMAND);

		return ResponseEntity
				.status(apiResponse.status())
				.body(apiResponse);
	}

//	// ResponseBody 파싱 실패
//	@Order(1)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<ErrorResponse<?>> handleFormatException(MethodArgumentNotValidException e) {
//
//		ErrorResponse<?> response = errorHandler.handleException(e, ErrorCode.INCORRECT_FORMAT);
//
//		return ResponseEntity
//				.status(response.getStatus())
//				.body(response);
//	}

	// Security 관련 예외
	@Order(1)
	@ExceptionHandler({
			AuthenticationException.class,
			AccessDeniedException.class,
			InternalAuthenticationServiceException.class}
	)
	public ResponseEntity<ErrorResponse<?>> handleFormatException(Exception e) {

		ErrorResponse<?> response = errorHandler.handleException(e, ErrorCode.SECURITY_ERROR);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}

	@Order(1)
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorResponse<List<ValidationError>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		ErrorResponse<List<ValidationError>> response = errorHandler.handleValidationException(e);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}

	// 이외의 나머지 오류
	@Order(Ordered.LOWEST_PRECEDENCE)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse<?>> handleApiException(RuntimeException e) {
		ErrorResponse<?> response = errorHandler.handleException(e, ErrorCode.UNKNOWN_ERROR);

		return ResponseEntity
				.status(response.status())
				.body(response);
	}
}

