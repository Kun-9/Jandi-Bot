package com.hk_music_cop.demo.global.config.advice;

import com.hk_music_cop.demo.global.error.dto.ErrorResponse;
import com.hk_music_cop.demo.global.error.ErrorHandler;
import com.hk_music_cop.demo.global.error.exceptions.CustomException;
import com.hk_music_cop.demo.lottery.presentation.LotteryController;
import com.hk_music_cop.demo.member.presentation.MemberController;
import com.hk_music_cop.demo.schedule.presentation.ScheduleController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

	private final ErrorHandler errorHandler;

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleApiException(CustomException e) {

		ErrorResponse errorResponse = errorHandler.handleException(e, HttpStatus.BAD_REQUEST);

		return ResponseEntity
				.badRequest()
				.body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleDateFormatException(MethodArgumentTypeMismatchException e) {
		String message = "날짜 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력해주세요.";

		ErrorResponse errorResponse = errorHandler.handleExceptionMessage(e, HttpStatus.BAD_REQUEST, message);

		return ResponseEntity
				.badRequest()
				.body(errorResponse);
	}
}

