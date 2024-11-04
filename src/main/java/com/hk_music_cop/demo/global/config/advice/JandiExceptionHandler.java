package com.hk_music_cop.demo.global.config.advice;

import com.hk_music_cop.demo.external.jandi.application.JandiMessageFactory;
import com.hk_music_cop.demo.global.error.jandi.JandiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class JandiExceptionHandler {

	JandiMessageFactory jandiMessageFactory;

	@ExceptionHandler(JandiException.class)
	public ResponseEntity<JSONObject> handelJandiException(JandiException e) {

		log.error(e.getMessage(), e);

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(jandiMessageFactory.errorMessage(e.toString()));
	}
}

