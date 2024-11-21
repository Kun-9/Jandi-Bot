package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import org.springframework.http.HttpEntity;

public interface JandiWebhookClient {

	/**
	 * 잔디 response 메시지 엔티티 생성
	 * @param webhookURL 웹훅 URL
	 * @param JandiWebhookResponse 잔디 요청 DTO
	 * @return Response Entity
	 */
	HttpEntity<String> sendWebhookRequest(String webhookURL, JandiWebhookResponse JandiWebhookResponse);

	/**
	 * 잔디 response 메시지 생성
	 * @param jandiWebhookResponse 잔디 요청 DTO
	 * @return Response JSON
	 */
	// JandiWebhookResponse 로 응답하기 때문에 사용 X (스프링에서 JSON 자동 변환됨)
//	JSONObject jandiResponseToJsonObject(JandiWebhookResponse jandiWebhookResponse);


	/**
	 * 잔디 ResponseDTO 로 파싱
	 * @param weeklySchedule 캘린더 일정 객체
	 * @return JandiWebhookResponse DTO
	 */
//	List<ConnectInfo> parseWeekScheduleToConnectInfo(WeeklySchedule weeklySchedule);
}
