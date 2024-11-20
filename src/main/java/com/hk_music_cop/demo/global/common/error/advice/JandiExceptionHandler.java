package com.hk_music_cop.demo.global.common.error.advice;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.application.JandiResponseGenerator;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.presentation.JandiWebhookController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(0)
@RestControllerAdvice(assignableTypes = {JandiWebhookController.class})
@RequiredArgsConstructor
@Slf4j
public class JandiExceptionHandler {

	private final JandiResponseGenerator jandiResponseGenerator;

	@ExceptionHandler(CustomException.class)
	public JandiWebhookResponse handelJandiCustomException(CustomException e) {
		return jandiResponseGenerator.createCustomErrorResponse(e);
	}

	@ExceptionHandler(Exception.class)
	public JandiWebhookResponse handelJandiException(Exception e) {
		System.out.println("JandiExceptionHandler.handelJandiException");

		return jandiResponseGenerator.createErrorResponse(e, ResponseCode.UNKNOWN_ERROR);
	}
}

