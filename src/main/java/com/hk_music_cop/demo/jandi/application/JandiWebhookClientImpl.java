package com.hk_music_cop.demo.jandi.application;

import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class JandiWebhookClientImpl implements JandiWebhookClient {

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
