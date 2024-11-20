package com.hk_music_cop.demo.global.common.error.advice;

import com.hk_music_cop.demo.global.common.response.ApiResponse;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.global.common.error.ErrorHandler;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	private final ErrorHandler errorHandler;

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<?>> handleApiCustomException(CustomException e) {

		ApiResponse<?> apiResponse = errorHandler.handleCustomException(e);

		return ResponseEntity
				.status(apiResponse.getStatus())
				.body(apiResponse);
	}


	// SQL, DB 오류
	@ExceptionHandler({SQLException.class, DataAccessException.class})
	public ResponseEntity<ApiResponse<?>> handleApiDBException(SQLException e) {

		ApiResponse<?> response = errorHandler.handleException(e, ResponseCode.DATABASE_ERROR);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}

	// 이외의 나머지 오류
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleApiException(Exception e) {
		ApiResponse<?> response = errorHandler.handleException(e, ResponseCode.UNKNOWN_ERROR);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);

	}


	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<?>> handleApiParamException(HttpMessageNotReadableException e) {

		ApiResponse<?> apiResponse = errorHandler.handleException(e, ResponseCode.UNDEFINED_COMMAND);

		return ResponseEntity
				.status(apiResponse.getStatus())
				.body(apiResponse);
	}


	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<?>> handleFormatException(MethodArgumentTypeMismatchException e) {

		ApiResponse<?> response = errorHandler.handleException(e, ResponseCode.INCORRECT_FORMAT);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}
}

