package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;

import java.util.List;


public interface JandiMessageFormatter {

	/**
	 * 잔디 response 메시지 엔티티 생성
	 * @param webhookURL 웹훅 URL
	 * @param JandiWebhookResponse 잔디 요청 DTO
	 * @return Response Entity
	 */
	HttpEntity<String> createResponseEntity(String webhookURL, JandiWebhookResponse JandiWebhookResponse);

	/**
	 * 잔디 response 메시지 생성
	 * @param jandiWebhookResponse 잔디 요청 DTO
	 * @return Response JSON
	 */
	JSONObject createResponseMessage(JandiWebhookResponse jandiWebhookResponse);


	/**
	 * 잔디 ResponseDTO로 파싱
	 * @param title 잔디 메시지 제목
	 * @param color 잔디 메시지 색상
	 * @param result 잔디 응답 메시지 배열
	 * @return JandiWebhookResponse DTO
	 */
	JandiWebhookResponse parseScheduleListToResponse(String title, String color, List<List<String>> result);



}
