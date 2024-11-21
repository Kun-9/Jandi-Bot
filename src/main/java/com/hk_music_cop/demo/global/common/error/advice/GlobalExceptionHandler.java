package com.hk_music_cop.demo.global.common.error.advice;

import com.hk_music_cop.demo.global.common.response.ApiResponse;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.security.sasl.AuthenticationException;
import java.sql.SQLException;


@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	private final ErrorHandler errorHandler;

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<?>> handleApiCustomException(CustomException e) {

		ApiResponse<?> apiResponse = errorHandler.handleCustomException(e);

		return ResponseEntity
				.status(apiResponse.getStatus())
				.body(apiResponse);
	}

	// SQL, DB 오류
	@Order(1)
	@ExceptionHandler({SQLException.class, DataAccessException.class})
	public ResponseEntity<ApiResponse<?>> handleApiDBException(SQLException e) {

		ApiResponse<?> response = errorHandler.handleException(e, ResponseCode.DATABASE_ERROR);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}

	// URL 경로, 쿼리파라미터 타입 변환 실패
	@Order(1)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<?>> handleApiParamException(HttpMessageNotReadableException e) {

		ApiResponse<?> apiResponse = errorHandler.handleException(e, ResponseCode.UNDEFINED_COMMAND);

		return ResponseEntity
				.status(apiResponse.getStatus())
				.body(apiResponse);
	}

	// ResponseBody 파싱 실패
	@Order(1)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<?>> handleFormatException(MethodArgumentTypeMismatchException e) {

		ApiResponse<?> response = errorHandler.handleException(e, ResponseCode.INCORRECT_FORMAT);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}

	// Security 관련 예외
	@Order(1)
	@ExceptionHandler({
			AuthenticationException.class,
			AccessDeniedException.class,
			InternalAuthenticationServiceException.class}
	)
	public ResponseEntity<ApiResponse<?>> handleFormatException(Exception e) {

		ApiResponse<?> response = errorHandler.handleException(e, ResponseCode.SECURITY_ERROR);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}

	// 이외의 나머지 오류
	@Order(Ordered.LOWEST_PRECEDENCE)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<?>> handleApiException(RuntimeException e) {
		ApiResponse<?> response = errorHandler.handleException(e, ResponseCode.UNKNOWN_ERROR);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}
}

