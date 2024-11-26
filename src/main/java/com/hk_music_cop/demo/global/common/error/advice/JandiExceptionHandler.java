package com.hk_music_cop.demo.global.common.error.advice;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.application.JandiErrorResponseGenerator;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
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

	private final JandiErrorResponseGenerator jandiErrorResponseGenerator;

	@ExceptionHandler(CustomException.class)
	public JandiWebhookResponse handelJandiCustomException(CustomException e) {
		return jandiErrorResponseGenerator.createCustomErrorResponse(e);
	}

	@ExceptionHandler(Exception.class)
	public JandiWebhookResponse handelJandiException(Exception e) {
		System.out.println("JandiExceptionHandler.handelJandiException");

		return jandiErrorResponseGenerator.createErrorResponse(e, ErrorCode.UNKNOWN_ERROR);
	}
}

