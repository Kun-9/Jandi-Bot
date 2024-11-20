package com.hk_music_cop.demo.global.common.error.advice;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.application.JandiMessageFactory;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
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
	public String handelJandiCustomException(CustomException e) {
		return jandiMessageFactory.customErrorMessage(e).toString();
	}

	@ExceptionHandler(Exception.class)
	public String handelJandiException(Exception e) {
		return jandiMessageFactory.errorMessage(e, ResponseCode.UNKNOWN_ERROR).toString();
	}
}

