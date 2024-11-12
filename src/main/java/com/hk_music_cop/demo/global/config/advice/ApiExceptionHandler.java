package com.hk_music_cop.demo.global.config.advice;

import com.hk_music_cop.demo.global.config.advice.dto.ErrorResponse;
import com.hk_music_cop.demo.global.error.common.CustomException;
import com.hk_music_cop.demo.lottery.presentation.LotteryController;
import com.hk_music_cop.demo.schedule.presentation.ScheduleController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice(assignableTypes = {ScheduleController.class, LotteryController.class})
public class ApiExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleApiException(CustomException e) {
		log.error("error: ", e);

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage()
		);

		return ResponseEntity
				.badRequest()
				.body(errorRㅉesponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleDateFormatException(MethodArgumentTypeMismatchException e) {
		log.error("Invalid date format: ", e);
		System.out.println("hello");

		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"날짜 형식이 올바르지 않습니다. yyyy-MM-dd 형식으로 입력해주세요."
		);
//		return ResponseEntity
//				.badRequest()
//				.body(errorResponse);
//		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(errorResponse, HttpStatus.OK);
	}
}

