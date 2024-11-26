package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.global.common.error.exceptions.CustomException;
import com.hk_music_cop.demo.global.common.response.ErrorCode;
import com.hk_music_cop.demo.global.common.response.ResponseCode;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;


public interface JandiErrorResponseGenerator {
	/**
	 * 커스텀 에러 응답 생성
	 * @param e 커스텀 예외
	 * @return 잔디 웹훅 응답
	 */
	JandiWebhookResponse createCustomErrorResponse(CustomException e);

	/**
	 * 일반 에러 응답 생성
	 * @param e 예외
	 * @param code 응답 코드
	 * @return 잔디 웹훅 응답
	 */
	JandiWebhookResponse createErrorResponse(Exception e, ErrorCode code);
}
