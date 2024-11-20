package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.schedule.domain.WeeklySchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse.ConnectInfo;

@Component
@RequiredArgsConstructor
public class JandiMessageFormatterRefactor implements JandiMessageFormatter {
	public List<ConnectInfo> parseWeekScheduleToConnectInfo(WeeklySchedule weeklySchedule) {
		// 일정이 있는지 검증
		if (validationScheduleEmpty(weeklySchedule)) {
			return null;
		}

		// WeeklySchedule을 ConnectInfo List 로 변환
		return ConnectInfo.from(weeklySchedule);
	}

	private boolean validationScheduleEmpty(WeeklySchedule weeklySchedule) {
		return weeklySchedule.isEmpty();
	}

	@Override
	public HttpEntity<String> sendWebhookRequest(String webhookURL, JandiWebhookResponse JandiWebhookResponse) {
		RestTemplate restTemplate = new RestTemplate();

		// Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", "application/vnd.tosslab.jandi-v2+json");

		// Http 엔티티 생성
		HttpEntity<JandiWebhookResponse> entity = new HttpEntity<>(JandiWebhookResponse, headers);

		return restTemplate.postForEntity(webhookURL, entity, String.class);
	}
}
