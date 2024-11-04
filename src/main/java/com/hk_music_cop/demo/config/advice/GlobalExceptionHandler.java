package com.hk_music_cop.demo.config.advice;

import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateMemberException.class)
	public ResponseEntity<ErrorResponse> handelDuplicateMemberException(DuplicateMemberException e) {
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage()
		);

		return ResponseEntity
				.badRequest()
				.body(errorResponse);
	}



}
