package com.hk_music_cop.demo.global.config.advice;

import com.hk_music_cop.demo.global.config.advice.dto.ErrorResponse;
import com.hk_music_cop.demo.global.error.common.CustomException;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handelDuplicateMemberException(CustomException e) {
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage()
		);

		return ResponseEntity
				.badRequest()
				.body(errorResponse);
	}
}

