package com.hk_music_cop.demo.schedule.api.jandi_webhook;

import com.hk_music_cop.demo.external.jandi.JandiProperties;
import com.hk_music_cop.demo.external.jandi.application.JandiMessageService;
import com.hk_music_cop.demo.external.jandi.dto.request.JandiWebhookResponse;
import com.hk_music_cop.demo.external.jandi.dto.response.JandiWebhookRequest;
import com.hk_music_cop.demo.lottery.application.LotteryService;
import com.hk_music_cop.demo.schedule.application.ScheduleService;
import com.hk_music_cop.demo.external.jandi.application.JandiMessageConverter;
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
	private final JandiMessageConverter jandiMessageConverter;
	private final LotteryService lotteryService;
	private final JandiProperties jandiProperties;
	private final JandiMessageService jandiMessageService;

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

		jandiMessageConverter.createJandiRequestMessageEntity(webhookURL, jandiWebhookResponse);

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
//				throw new IllegalArgumentException("알 수 없는 명령");
				return errorMessage();
		}
	}

	private String errorMessage() {
		return jandiMessageService.errorMessage().toString();
	}


	private String weekSchedule() {
		LocalDate date = LocalDate.now();

		String title = "이번주 일정";
		String color = jandiProperties.getColor().getSuccessColor();
		return jandiMessageService.scheduleWeekMessage(date).toString();
	}


	private String todaySchedule() {
		LocalDate date = LocalDate.now();

		String title = "오늘 일정";
		String color = jandiProperties.getColor().getSuccessColor();
		return jandiMessageService.scheduleDayMessage(date).toString();
	}

	private String lotteryResult() {
		String title = "랜덤 추첨";
		String color = jandiProperties.getColor().getSuccessColor();
		return lotteryService.chooseLotteryWinner(title, color, null).toString();
	}

	private String myInfo(JandiWebhookRequest jandiWebhookRequest) {
		String title = "내 정보";
		String color = jandiProperties.getColor().getSuccessColor();

		return null;
	}

	private static StringBuilder getWriterInfo(JandiWebhookRequest jandiWebhookRequest) {
		StringBuilder writerInfo = new StringBuilder();
		writerInfo
				.append("일자 : ").append(jandiWebhookRequest.getCreatedAt()).append("\n")
				.append("ID : ").append(jandiWebhookRequest.getWriter().getId()).append("\n")
				.append("이름 : ").append(jandiWebhookRequest.getWriter().getName()).append("\n")
				.append("E-Mail : ").append(jandiWebhookRequest.getWriter().getEmail()).append("\n")
				.append("TEL : ").append(jandiWebhookRequest.getWriter().getPhoneNumber()).append("\n")
				.append("키워드 : ").append(jandiWebhookRequest.getKeyword()).append("\n")
				.append("IP : ").append(jandiWebhookRequest.getIp()).append("\n")
				.append("요청 방 이름 : ").append(jandiWebhookRequest.getRoomName()).append("\n")
				.append("팀 이름 : ").append(jandiWebhookRequest.getTeamName()).append("\n")
				.append("DATA : ").append(jandiWebhookRequest.getData()).append("\n")
				.append("TEXT : ").append(jandiWebhookRequest.getText()).append("\n")
				.append("토큰 : ").append(jandiWebhookRequest.getToken()).append("\n")
				.append("플랫폼 : ").append(jandiWebhookRequest.getPlatform()).append("\n");
		return writerInfo;
	}
}
