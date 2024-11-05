package com.hk_music_cop.demo.global.config.advice;

import com.hk_music_cop.demo.external.jandi.application.JandiCommandService;
import com.hk_music_cop.demo.external.jandi.application.JandiMessageFactory;
import com.hk_music_cop.demo.external.jandi.presentation.JandiWebhookController;
import com.hk_music_cop.demo.global.error.jandi.JandiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {JandiWebhookController.class})
@RequiredArgsConstructor
@Slf4j
public class JandiExceptionHandler {

	private final JandiMessageFactory jandiMessageFactory;

	@ExceptionHandler(RuntimeException.class)
	public String handelJandiException(RuntimeException e) {

		log.error(e.getMessage(), e);

		return jandiMessageFactory.errorMessage(e.getMessage()).toString();
	}
}

