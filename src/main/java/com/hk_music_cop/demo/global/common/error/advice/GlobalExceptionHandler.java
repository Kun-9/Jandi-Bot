package com.hk_music_cop.demo.global.common.error.advice;

import com.hk_music_cop.demo.global.common.response.ApiResponse;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.global.common.error.ErrorHandler;
import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

	private final ErrorHandler errorHandler;

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<?>> handleApiException(CustomException e) {

		ApiResponse<?> apiResponse = errorHandler.handleCustomException(e);

		return ResponseEntity
				.status(apiResponse.getStatus())
				.body(apiResponse);
	}


	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<?>> handleApiException(HttpMessageNotReadableException e) {

		ApiResponse<?> apiResponse = errorHandler.handleException(e, ResponseCode.UNDEFINED_COMMAND);

		return ResponseEntity
				.status(apiResponse.getStatus())
				.body(apiResponse);
	}


	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<?>> handleDateFormatException(MethodArgumentTypeMismatchException e) {

		ApiResponse<?> response = errorHandler.handleException(e, ResponseCode.INCORRECT_FORMAT);

		return ResponseEntity
				.status(response.getStatus())
				.body(response);
	}
}

