package com.hk_music_cop.demo.jandi.presentation;

import com.hk_music_cop.demo.jandi.application.JandiCommandServiceImpl;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.jandi.application.JandiMessageFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JandiWebhookController {

	private final JandiMessageFormatter jandiMessageFormatter;
	private final JandiCommandServiceImpl jandiCommandServiceImpl;

	@PostMapping("/jandi/message")
	public ResponseEntity<String> sendMessage(String webhookURL, String content, String color, String title, String description) {
		RestTemplate restTemplate = new RestTemplate();

		JandiWebhookResponse response = JandiWebhookResponse.createResponseBase(title, color);
		JandiWebhookResponse.ConnectInfo connectInfo = new JandiWebhookResponse.ConnectInfo(title, description, null);

		JandiWebhookResponse jandiWebhookResponse = response.withConnectInfo(connectInfo);

		// web hook url 설정
		webhookURL = "https://wh.jandi.com/connect-api/webhook/23002156/ad2476253597a22daaecdb0961fd25bd";

		HttpEntity<String> stringHttpEntity = jandiMessageFormatter.sendWebhookRequest(webhookURL, jandiWebhookResponse);

		return restTemplate.postForEntity(webhookURL, stringHttpEntity, String.class);
	}

	@PostMapping("/jandi")
	public JandiWebhookResponse sendJandiMessage(@RequestBody JandiWebhookRequest jandiWebhookRequest) {
		return jandiCommandServiceImpl.executeCommand(jandiWebhookRequest);
	}
}
