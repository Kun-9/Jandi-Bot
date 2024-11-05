package com.hk_music_cop.demo.global.config.advice;

import com.hk_music_cop.demo.global.error.common.CustomException;
import com.hk_music_cop.demo.jandi.application.JandiMessageFactory;
import com.hk_music_cop.demo.jandi.presentation.JandiWebhookController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {JandiWebhookController.class})
@RequiredArgsConstructor
@Slf4j
public class JandiExceptionHandler {

	private final JandiMessageFactory jandiMessageFactory;

	@ExceptionHandler(CustomException.class)
	public String handelJandiException(CustomException e) {

		log.error(e.getMessage(), e);

		return jandiMessageFactory.errorMessage(e.getMessage()).toString();
	}
}

