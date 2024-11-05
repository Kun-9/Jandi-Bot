package com.hk_music_cop.demo.jandi.presentation;

import com.hk_music_cop.demo.jandi.application.JandiCommandService;
import com.hk_music_cop.demo.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.jandi.application.JandiMessageFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JandiWebhookController {

	private final JandiMessageFormatter jandiMessageFormatter;
	private final JandiCommandService jandiCommandService;

	@PostMapping("/jandi/message")
	public String sendMessage(String webhookURL, String content, String color, String title, String description) {

//		String content = "test content";
//		String color = "#FAC11B";
//		String title = "test title";
//		String description = "test description";

		JandiWebhookResponse jandiWebhookResponse = new JandiWebhookResponse(content, color);
		jandiWebhookResponse.addConnectInfo(new JandiWebhookResponse.ConnectInfo(title, description, null));

		// web hook url 설정
		webhookURL = "https://wh.jandi.com/connect-api/webhook/23002156/ad2476253597a22daaecdb0961fd25bd";

		jandiMessageFormatter.createResponseEntity(webhookURL, jandiWebhookResponse);

//		ResponseEntity<String> response = restTemplate.postForEntity(webhookURL, entity, String.class);

		return "redirect:/jandi";
	}

	@ResponseBody
	@PostMapping("/jandi")
	public String sendJandiMessage(@RequestBody JandiWebhookRequest jandiWebhookRequest) {
		return jandiCommandService.executeCommand(jandiWebhookRequest).toString();
	}
}
