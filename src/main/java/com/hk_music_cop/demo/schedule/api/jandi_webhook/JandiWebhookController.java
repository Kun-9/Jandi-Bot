package com.hk_music_cop.demo.schedule.api.jandi_webhook;

import com.hk_music_cop.demo.external.jandi.JandiProperties;
import com.hk_music_cop.demo.external.jandi.application.JandiMessageFactory;
import com.hk_music_cop.demo.external.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.global.error.jandi.JandiUndefinedCommand;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.schedule.application.ScheduleService;
import com.hk_music_cop.demo.external.jandi.application.JandiMessageFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JandiWebhookController {

	private final ScheduleService scheduleService;
	private final JandiMessageFormatter jandiMessageFormatter;
	private final LotteryService lotteryService;
	private final JandiProperties jandiProperties;
	private final JandiMessageFactory jandiMessageFactory;

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

		jandiMessageFormatter.createJandiRequestMessageEntity(webhookURL, jandiWebhookResponse);

//		ResponseEntity<String> response = restTemplate.postForEntity(webhookURL, entity, String.class);

		return "redirect:/jandi";
	}

	@ResponseBody
	@PostMapping("/jandi")
	public String sendJandiMessage(@RequestBody JandiWebhookRequest jandiWebhookRequest) {

		StringBuilder writerInfo = getWriterInfo(jandiWebhookRequest);

		log.info(writerInfo.toString());

		String keyword = jandiWebhookRequest.getData();

		JSONObject jandiSendMessage;

		switch (keyword) {
			case "이번주 일정":
				return weekSchedule();
			case "오늘 일정":
				return todaySchedule();
			case "랜덤 추첨":
				return lotteryResult();
			case "내 정보":
				return myInfo(jandiWebhookRequest);
			default:
				throw new JandiUndefinedCommand(keyword);
		}
	}



	private String weekSchedule() {
		LocalDate date = LocalDate.now();

		return jandiMessageFactory.scheduleWeekMessage(date).toString();
	}


	private String todaySchedule() {
		LocalDate date = LocalDate.now();

		return jandiMessageFactory.scheduleDayMessage(date).toString();
	}

	private String lotteryResult() {
		String imgURL = null;

		return jandiMessageFactory.lotteryMessage(imgURL).toString();
	}

	private String myInfo(JandiWebhookRequest jandiWebhookRequest) {

		return null;
	}
}
